/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrderedMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class IrFactoryImpl extends EFactoryImpl implements IrFactory {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IrPackage getPackage() {
		return IrPackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static IrFactory init() {
		try {
			IrFactory theIrFactory = (IrFactory) EPackage.Registry.INSTANCE
					.getEFactory("http:///net/sf/orcc/ir.ecore");
			if (theIrFactory != null) {
				return theIrFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IrFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public IrFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case IrPackage.TYPE_BOOL:
			return createTypeBool();
		case IrPackage.TYPE_FLOAT:
			return createTypeFloat();
		case IrPackage.TYPE_INT:
			return createTypeInt();
		case IrPackage.TYPE_LIST:
			return createTypeList();
		case IrPackage.TYPE_STRING:
			return createTypeString();
		case IrPackage.TYPE_UINT:
			return createTypeUint();
		case IrPackage.TYPE_VOID:
			return createTypeVoid();
		case IrPackage.NODE_BLOCK:
			return createNodeBlock();
		case IrPackage.NODE_IF:
			return createNodeIf();
		case IrPackage.NODE_WHILE:
			return createNodeWhile();
		case IrPackage.PROCEDURE:
			return createProcedure();
		case IrPackage.INST_ASSIGN:
			return createInstAssign();
		case IrPackage.INST_CALL:
			return createInstCall();
		case IrPackage.INST_LOAD:
			return createInstLoad();
		case IrPackage.INST_PHI:
			return createInstPhi();
		case IrPackage.INST_RETURN:
			return createInstReturn();
		case IrPackage.INST_SPECIFIC:
			return createInstSpecific();
		case IrPackage.INST_STORE:
			return createInstStore();
		case IrPackage.LOCATION:
			return createLocation();
		case IrPackage.VAR:
			return createVar();
		case IrPackage.USE:
			return createUse();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName()
					+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InstAssign createInstAssign() {
		InstAssignImpl instAssign = new InstAssignImpl();
		return instAssign;
	}

	@Override
	public InstAssign createInstAssign(Location loc, Var target,
			Expression value) {
		InstAssignImpl instAssign = new InstAssignImpl();
		instAssign.setLocation(loc);
		instAssign.setTarget(target);
		instAssign.setValue(value);
		return instAssign;
	}

	@Override
	public InstAssign createInstAssign(Var target, Expression value) {
		InstAssignImpl instAssign = new InstAssignImpl();
		instAssign.setLocation(IrFactory.eINSTANCE.createLocation());
		instAssign.setTarget(target);
		instAssign.setValue(value);
		return instAssign;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InstCall createInstCall() {
		InstCallImpl instCall = new InstCallImpl();
		return instCall;
	}

	@Override
	public InstCall createInstCall(Location location, Var target,
			Procedure procedure, List<Expression> parameters) {
		InstCallImpl instCall = new InstCallImpl();
		instCall.setLocation(location);
		instCall.setTarget(target);
		instCall.setProcedure(procedure);
		instCall.getParameters().addAll(parameters);
		return instCall;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InstLoad createInstLoad() {
		InstLoadImpl instLoad = new InstLoadImpl();
		return instLoad;
	}

	@Override
	public InstLoad createInstLoad(Location location, Var target, Use source,
			List<Expression> indexes) {
		InstLoadImpl instLoad = new InstLoadImpl();
		instLoad.setLocation(location);
		instLoad.setTarget(target);
		instLoad.setSource(source);
		instLoad.getIndexes().addAll(indexes);
		return instLoad;
	}

	@Override
	public InstLoad createInstLoad(Var target, Use source) {
		InstLoadImpl instLoad = new InstLoadImpl();
		instLoad.setLocation(IrFactory.eINSTANCE.createLocation());
		instLoad.setTarget(target);
		instLoad.setSource(source);
		return instLoad;
	}

	@Override
	public InstLoad createInstLoad(Var target, Use source,
			List<Expression> indexes) {
		InstLoadImpl instLoad = new InstLoadImpl();
		instLoad.setLocation(IrFactory.eINSTANCE.createLocation());
		instLoad.setTarget(target);
		instLoad.setSource(source);
		instLoad.getIndexes().addAll(indexes);
		return instLoad;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InstPhi createInstPhi() {
		InstPhiImpl instPhi = new InstPhiImpl();
		return instPhi;
	}

	@Override
	public InstPhi createInstPhi(Location location, Var target,
			List<Expression> values) {
		InstPhiImpl instPhi = new InstPhiImpl();
		instPhi.setLocation(location);
		instPhi.setTarget(target);
		instPhi.getValues().addAll(values);
		return instPhi;
	}

	@Override
	public InstPhi createInstPhi(Var target, List<Expression> values) {
		InstPhiImpl instPhi = new InstPhiImpl();
		instPhi.setLocation(IrFactory.eINSTANCE.createLocation());
		instPhi.setTarget(target);
		instPhi.getValues().addAll(values);
		return instPhi;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InstReturn createInstReturn() {
		InstReturnImpl instReturn = new InstReturnImpl();
		return instReturn;
	}

	@Override
	public InstReturn createInstReturn(Expression value) {
		InstReturnImpl instReturn = new InstReturnImpl();
		instReturn.setLocation(IrFactory.eINSTANCE.createLocation());
		instReturn.setValue(value);
		return instReturn;
	}

	@Override
	public InstReturn createInstReturn(Location location, Expression value) {
		InstReturnImpl instReturn = new InstReturnImpl();
		instReturn.setLocation(location);
		instReturn.setValue(value);
		return instReturn;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InstSpecific createInstSpecific() {
		InstSpecificImpl instSpecific = new InstSpecificImpl();
		return instSpecific;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InstStore createInstStore() {
		InstStoreImpl instStore = new InstStoreImpl();
		return instStore;
	}

	@Override
	public InstStore createInstStore(Location location, Var target,
			List<Expression> indexes, Expression value) {
		InstStoreImpl instStore = new InstStoreImpl();
		instStore.setLocation(location);
		instStore.setTarget(target);
		instStore.setValue(value);
		instStore.getIndexes().addAll(indexes);
		return instStore;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Location createLocation() {
		LocationImpl location = new LocationImpl();
		return location;
	}

	@Override
	public Location createLocation(int startLine, int startColumn, int endColumn) {
		LocationImpl location = new LocationImpl();
		location.setStartLine(startLine);
		location.setStartColumn(startColumn);
		location.setEndColumn(endColumn);
		return location;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NodeBlock createNodeBlock() {
		NodeBlockImpl nodeBlock = new NodeBlockImpl();
		return nodeBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NodeIf createNodeIf() {
		NodeIfImpl nodeIf = new NodeIfImpl();
		return nodeIf;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NodeWhile createNodeWhile() {
		NodeWhileImpl nodeWhile = new NodeWhileImpl();
		return nodeWhile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Procedure createProcedure() {
		ProcedureImpl procedure = new ProcedureImpl();
		return procedure;
	}

	@Override
	public Procedure createProcedure(String name, boolean nativeFlag,
			Location location, Type returnType,
			OrderedMap<String, Var> parameters, OrderedMap<String, Var> locals,
			List<Node> nodes) {
		ProcedureImpl procedure = new ProcedureImpl();

		procedure.setLocation(location);
		procedure.setNative(nativeFlag);
		procedure.getNodes().addAll(nodes);
		procedure.setName(name);
		procedure.setReturnType(returnType);

		procedure.setLocals(locals);
		procedure.setParameters(parameters);

		return procedure;
	}

	@Override
	public Procedure createProcedure(String name, Location location,
			Type returnType) {
		ProcedureImpl procedure = new ProcedureImpl();

		procedure.setLocation(location);
		procedure.setName(name);
		procedure.setReturnType(returnType);

		procedure.setLocals(new OrderedMap<String, Var>());
		procedure.setParameters(new OrderedMap<String, Var>());

		return procedure;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeBool createTypeBool() {
		TypeBoolImpl typeBool = new TypeBoolImpl();
		return typeBool;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeFloat createTypeFloat() {
		TypeFloatImpl typeFloat = new TypeFloatImpl();
		return typeFloat;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeInt createTypeInt() {
		TypeIntImpl typeInt = new TypeIntImpl();
		return typeInt;
	}

	@Override
	public TypeInt createTypeInt(int size) {
		TypeIntImpl intType = new TypeIntImpl();
		intType.setSize(size);
		return intType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeList createTypeList() {
		TypeListImpl typeList = new TypeListImpl();
		return typeList;
	}

	@Override
	public TypeList createTypeList(Expression size, Type type) {
		TypeListImpl listType = new TypeListImpl();
		listType.setSizeExpr(size);
		listType.setType(type);
		return listType;
	}

	@Override
	public TypeList createTypeList(int size, Type type) {
		TypeListImpl listType = new TypeListImpl();
		listType.setSize(size);
		listType.setType(type);
		return listType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeString createTypeString() {
		TypeStringImpl typeString = new TypeStringImpl();
		return typeString;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeUint createTypeUint() {
		TypeUintImpl typeUint = new TypeUintImpl();
		return typeUint;
	}

	@Override
	public TypeUint createTypeUint(int size) {
		TypeUintImpl typeUint = new TypeUintImpl();
		typeUint.setSize(size);
		return typeUint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeVoid createTypeVoid() {
		TypeVoidImpl typeVoid = new TypeVoidImpl();
		return typeVoid;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Use createUse() {
		UseImpl use = new UseImpl();
		return use;
	}

	@Override
	public Use createUse(Var variable) {
		UseImpl use = new UseImpl();
		use.setVariable(variable);
		return use;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Var createVar() {
		VarImpl var = new VarImpl();
		return var;
	}

	@Override
	public Var createVar(Location location, Type type, String name,
			boolean global, boolean assignable) {
		VarImpl var = new VarImpl();
		var.setAssignable(assignable);
		var.setGlobal(global);
		var.setLocation(location);
		var.setName(name);
		var.setType(type);
		return var;
	}

	@Override
	public Var createVar(Location location, Type type, String name,
			boolean assignable, Expression initialValue) {
		VarImpl var = new VarImpl();
		var.setAssignable(assignable);
		var.setGlobal(true);
		var.setInitialValue(initialValue);
		var.setLocation(location);
		var.setName(name);
		var.setType(type);
		return var;
	}

	@Override
	public Var createVar(Location location, Type type, String name,
			boolean assignable, int index) {
		VarImpl var = new VarImpl();
		var.setAssignable(assignable);
		var.setGlobal(false);
		var.setIndex(index);
		var.setLocation(location);
		var.setName(name);
		var.setType(type);
		return var;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IrPackage getIrPackage() {
		return (IrPackage) getEPackage();
	}

} // IrFactoryImpl
