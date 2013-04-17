package com.redhat.ceylon.compiler.java.runtime.metamodel;

import java.util.HashMap;
import java.util.Map;

import ceylon.language.Null;

import com.redhat.ceylon.cmr.api.ArtifactResult;
import com.redhat.ceylon.cmr.api.Logger;
import com.redhat.ceylon.cmr.api.RepositoryManager;
import com.redhat.ceylon.cmr.api.RepositoryManagerBuilder;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.RuntimeModuleManager;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.compiler.loader.impl.reflect.mirror.ReflectionClass;
import com.redhat.ceylon.compiler.loader.model.LazyClass;
import com.redhat.ceylon.compiler.loader.model.LazyInterface;
import com.redhat.ceylon.compiler.typechecker.context.Context;
import com.redhat.ceylon.compiler.typechecker.io.VFS;
import com.redhat.ceylon.compiler.typechecker.model.ProducedType;
import com.redhat.ceylon.compiler.typechecker.model.TypeDeclaration;

public class Metamodel {

    private static RuntimeModuleManager moduleManager;
    
    static{
        resetModuleManager();
    }

    // FIXME: this will need better thinking in terms of memory usage
    private static Map<com.redhat.ceylon.compiler.typechecker.model.ClassOrInterface, com.redhat.ceylon.compiler.java.runtime.metamodel.ClassOrInterface> typeCheckModelToRuntimeModel
        = new HashMap<com.redhat.ceylon.compiler.typechecker.model.ClassOrInterface, com.redhat.ceylon.compiler.java.runtime.metamodel.ClassOrInterface>();

    public static void loadModule(String name, String version, ArtifactResult result, ClassLoader classLoader){
        moduleManager.loadModule(name, version, result, classLoader);
    }
    
    public static void resetModuleManager() {
        RepositoryManagerBuilder builder = new RepositoryManagerBuilder(new Logger(){

            @Override
            public void error(String str) {
                System.err.println("ERROR: "+str);
            }

            @Override
            public void warning(String str) {
                System.err.println("WARN: "+str);
            }

            @Override
            public void info(String str) {
                System.err.println("INFO: "+str);
            }

            @Override
            public void debug(String str) {
                System.err.println("DEBUG: "+str);
            }
            
        });
        RepositoryManager repoManager = builder.buildRepository();
        VFS vfs = new VFS();
        Context context = new Context(repoManager, vfs);
        moduleManager = new RuntimeModuleManager(context);
        moduleManager.initCoreModules();
        moduleManager.prepareForTypeChecking();
    }

    public static TypeDescriptor getTypeDescriptor(Object instance) {
        if(instance == null)
            return Null.$TypeDescriptor;
        else if(instance instanceof ReifiedType)
            return((ReifiedType) instance).$getType();
        else
            return null; // FIXME: interop?
    }
    public static boolean isReified(java.lang.Object o, TypeDescriptor type){
        TypeDescriptor instanceType = getTypeDescriptor(o);
        if(instanceType == null)
            return false; // FIXME: interop?
        return instanceType.toProducedType(moduleManager).isSubtypeOf(type.toProducedType(moduleManager));
    }

    public static ProducedType getProducedType(Object instance) {
        TypeDescriptor instanceType = getTypeDescriptor(instance);
        if(instanceType == null)
            throw new RuntimeException("Metamodel not yet supported for Java types");
        return instanceType.toProducedType(moduleManager);
    }

    public static ceylon.language.metamodel.ProducedType getMetamodel(TypeDescriptor typeDescriptor) {
        if(typeDescriptor == null)
            throw new RuntimeException("Metamodel not yet supported for Java types");
        ProducedType pt = typeDescriptor.toProducedType(moduleManager);
        return getMetamodel(pt);
    }
    
    public static com.redhat.ceylon.compiler.java.runtime.metamodel.ClassOrInterface getOrCreateMetamodel(com.redhat.ceylon.compiler.typechecker.model.ClassOrInterface declaration){
        synchronized(typeCheckModelToRuntimeModel){
            com.redhat.ceylon.compiler.java.runtime.metamodel.ClassOrInterface ret = typeCheckModelToRuntimeModel.get(declaration);
            if(ret == null){
                if(declaration instanceof com.redhat.ceylon.compiler.typechecker.model.Class){
                    ret = new com.redhat.ceylon.compiler.java.runtime.metamodel.Class((com.redhat.ceylon.compiler.typechecker.model.Class)declaration); 
                }else if(declaration instanceof com.redhat.ceylon.compiler.typechecker.model.Interface){
                    ret = new com.redhat.ceylon.compiler.java.runtime.metamodel.Interface((com.redhat.ceylon.compiler.typechecker.model.Interface)declaration);
                }
                typeCheckModelToRuntimeModel.put(declaration, ret);
            }
            return ret;
        }
    }
    
    public static ceylon.language.metamodel.ProducedType getMetamodel(ProducedType pt) {
        TypeDeclaration declaration = pt.getDeclaration();
        if(declaration instanceof com.redhat.ceylon.compiler.typechecker.model.Class){
            return new com.redhat.ceylon.compiler.java.runtime.metamodel.ClassType(pt);
        }
        if(declaration instanceof com.redhat.ceylon.compiler.typechecker.model.Interface){
            return new com.redhat.ceylon.compiler.java.runtime.metamodel.InterfaceType(pt);
        }
        throw new RuntimeException("Declaration type not supported yet: "+declaration);
    }

    public static TypeDescriptor getTypeDescriptorForDeclaration(com.redhat.ceylon.compiler.typechecker.model.Declaration declaration) {
        if(declaration instanceof LazyClass){
            ReflectionClass classMirror = (ReflectionClass) ((LazyClass) declaration).classMirror;
            return TypeDescriptor.klass(classMirror.klass);
        }
        if(declaration instanceof LazyInterface){
            ReflectionClass classMirror = (ReflectionClass) ((LazyInterface) declaration).classMirror;
            return TypeDescriptor.klass(classMirror.klass);
        }
        throw new RuntimeException("Unsupported declaration type: " + declaration);
    }

}