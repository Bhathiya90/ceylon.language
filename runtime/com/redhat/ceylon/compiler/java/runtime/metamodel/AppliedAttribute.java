package com.redhat.ceylon.compiler.java.runtime.metamodel;

import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.model.Attribute$impl;
import ceylon.language.model.ValueModel$impl;
import ceylon.language.model.Model$impl;
import ceylon.language.model.Value;
import ceylon.language.model.declaration.ValueDeclaration;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;
import com.redhat.ceylon.compiler.java.metadata.Variance;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.compiler.typechecker.model.Generic;
import com.redhat.ceylon.compiler.typechecker.model.ProducedTypedReference;

@Ceylon(major = 5)
@com.redhat.ceylon.compiler.java.metadata.Class
@TypeParameters({
    @TypeParameter(value = "Container", variance = Variance.IN),
    @TypeParameter(value = "Type", variance = Variance.OUT),
})
public class AppliedAttribute<Container, Type> 
    extends AppliedMember<Container, ceylon.language.model.Value<? extends Type>>
    implements ceylon.language.model.Attribute<Container, Type> {

    protected FreeAttribute declaration;
    protected ProducedTypedReference typedReference;
    private ceylon.language.model.Type<? extends Type> closedType;
    @Ignore
    protected final TypeDescriptor $reifiedType;
    
    @Override
    public String toString() {
        return Metamodel.getProducedTypedReferenceString(typedReference);
    }
    
    public AppliedAttribute(@Ignore TypeDescriptor $reifiedContainer, 
                            @Ignore TypeDescriptor $reifiedType,
                            FreeAttribute declaration, ProducedTypedReference typedReference,
                            ceylon.language.model.ClassOrInterface<? extends Object> container) {
        super($reifiedContainer, TypeDescriptor.klass(ceylon.language.model.Value.class, $reifiedType), container);
        this.declaration = declaration;
        this.typedReference = typedReference;
        this.closedType = Metamodel.getAppliedMetamodel(typedReference.getType());
        this.$reifiedType = $reifiedType;
    }

    @Override
    @Ignore
    public ValueModel$impl<Type> $ceylon$language$model$ValueModel$impl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Ignore
    public Model$impl $ceylon$language$model$Model$impl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Ignore
    public Attribute$impl<Container, Type> $ceylon$language$model$Attribute$impl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @TypeInfo("ceylon.language.model.declaration::ValueDeclaration")
    public ValueDeclaration getDeclaration() {
        return declaration;
    }

    @Override
    @TypeInfo("ceylon.language.model::Type<Type>")
    public ceylon.language.model.Type<? extends Type> getType() {
        return closedType;
    }
    
    @Override
    protected Value<? extends Type> bindTo(Object instance) {
        return new AppliedValue($reifiedType, declaration, typedReference, instance);
    }

    @Ignore
    @Override
    public TypeDescriptor $getType() {
        return TypeDescriptor.klass(AppliedAttribute.class, super.$reifiedType, $reifiedType);
    }

    public static ceylon.language.model.Attribute instance(@Ignore TypeDescriptor $reifiedSubType, @Ignore TypeDescriptor reifiedValueType, 
                                                               FreeAttribute value, ProducedTypedReference valueTypedReference, 
                                                               com.redhat.ceylon.compiler.typechecker.model.TypedDeclaration decl,
                                                               ceylon.language.model.ClassOrInterface<? extends Object> container) {
        return decl.isVariable()
                ? new AppliedVariableAttribute($reifiedSubType, reifiedValueType, value, valueTypedReference, container)
                : new AppliedAttribute($reifiedSubType, reifiedValueType, value, valueTypedReference, container);
    }
    
    @Override
    @Ignore
    public Value<? extends Type> $call$variadic() {
        return $call$variadic(empty_.$get());
    }
    
    @Override
    @Ignore
    public Value<? extends Type> $call$variadic(
            Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Value<? extends Type> $call$variadic(
            Object arg0, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Value<? extends Type> $call$variadic(
            Object arg0, Object arg1, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Value<? extends Type> $call$variadic(
            Object arg0, Object arg1, Object arg2, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Value<? extends Type> $call$variadic(Object... argsAndVarargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Value<? extends Type> $call$variadic(Object arg0) {
        return $call$variadic(arg0, empty_.$get());
    }

    @Override
    @Ignore
    public Value<? extends Type> $call$variadic(Object arg0, Object arg1) {
        return $call$variadic(arg0, arg1, empty_.$get());
    }

    @Override
    @Ignore
    public Value<? extends Type> $call$variadic(Object arg0, Object arg1,
            Object arg2) {
        return $call$variadic(arg0, arg1, arg2, empty_.$get());
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + getDeclaringClassOrInterface().hashCode();
        result = 37 * result + getDeclaration().hashCode();
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof ceylon.language.model.Attribute == false)
            return false;
        ceylon.language.model.Attribute other = (ceylon.language.model.Attribute) obj;
        return getDeclaration().equals(other.getDeclaration())
                && getDeclaringClassOrInterface().equals(other.getDeclaringClassOrInterface());
    }
}
