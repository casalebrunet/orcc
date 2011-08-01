/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Action</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.ActionImpl#getBody <em>Body</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActionImpl#getInputPattern <em>Input Pattern</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActionImpl#getOutputPattern <em>Output Pattern</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActionImpl#getPeekPattern <em>Peek Pattern</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActionImpl#getScheduler <em>Scheduler</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActionImpl#getTag <em>Tag</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActionImpl extends EObjectImpl implements Action {

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Procedure body;

	/**
	 * The cached value of the '{@link #getInputPattern() <em>Input Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern inputPattern;

	/**
	 * The cached value of the '{@link #getOutputPattern() <em>Output Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern outputPattern;

	/**
	 * The cached value of the '{@link #getPeekPattern() <em>Peek Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPeekPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern peekPattern;

	/**
	 * The cached value of the '{@link #getScheduler() <em>Scheduler</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getScheduler()
	 * @generated
	 * @ordered
	 */
	protected Procedure scheduler;

	/**
	 * The cached value of the '{@link #getTag() <em>Tag</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTag()
	 * @generated
	 * @ordered
	 */
	protected Tag tag;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(Procedure newBody,
			NotificationChain msgs) {
		Procedure oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__BODY, oldBody, newBody);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInputPattern(Pattern newInputPattern,
			NotificationChain msgs) {
		Pattern oldInputPattern = inputPattern;
		inputPattern = newInputPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__INPUT_PATTERN, oldInputPattern, newInputPattern);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOutputPattern(Pattern newOutputPattern,
			NotificationChain msgs) {
		Pattern oldOutputPattern = outputPattern;
		outputPattern = newOutputPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__OUTPUT_PATTERN, oldOutputPattern, newOutputPattern);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPeekPattern(Pattern newPeekPattern, NotificationChain msgs) {
		Pattern oldPeekPattern = peekPattern;
		peekPattern = newPeekPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__PEEK_PATTERN, oldPeekPattern, newPeekPattern);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScheduler(Procedure newScheduler,
			NotificationChain msgs) {
		Procedure oldScheduler = scheduler;
		scheduler = newScheduler;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__SCHEDULER, oldScheduler, newScheduler);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTag(Tag newTag, NotificationChain msgs) {
		Tag oldTag = tag;
		tag = newTag;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__TAG, oldTag, newTag);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.ACTION__BODY:
				return getBody();
			case IrPackage.ACTION__INPUT_PATTERN:
				return getInputPattern();
			case IrPackage.ACTION__OUTPUT_PATTERN:
				return getOutputPattern();
			case IrPackage.ACTION__PEEK_PATTERN:
				return getPeekPattern();
			case IrPackage.ACTION__SCHEDULER:
				return getScheduler();
			case IrPackage.ACTION__TAG:
				return getTag();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IrPackage.ACTION__BODY:
				return basicSetBody(null, msgs);
			case IrPackage.ACTION__INPUT_PATTERN:
				return basicSetInputPattern(null, msgs);
			case IrPackage.ACTION__OUTPUT_PATTERN:
				return basicSetOutputPattern(null, msgs);
			case IrPackage.ACTION__PEEK_PATTERN:
				return basicSetPeekPattern(null, msgs);
			case IrPackage.ACTION__SCHEDULER:
				return basicSetScheduler(null, msgs);
			case IrPackage.ACTION__TAG:
				return basicSetTag(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IrPackage.ACTION__BODY:
				return body != null;
			case IrPackage.ACTION__INPUT_PATTERN:
				return inputPattern != null;
			case IrPackage.ACTION__OUTPUT_PATTERN:
				return outputPattern != null;
			case IrPackage.ACTION__PEEK_PATTERN:
				return peekPattern != null;
			case IrPackage.ACTION__SCHEDULER:
				return scheduler != null;
			case IrPackage.ACTION__TAG:
				return tag != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IrPackage.ACTION__BODY:
				setBody((Procedure)newValue);
				return;
			case IrPackage.ACTION__INPUT_PATTERN:
				setInputPattern((Pattern)newValue);
				return;
			case IrPackage.ACTION__OUTPUT_PATTERN:
				setOutputPattern((Pattern)newValue);
				return;
			case IrPackage.ACTION__PEEK_PATTERN:
				setPeekPattern((Pattern)newValue);
				return;
			case IrPackage.ACTION__SCHEDULER:
				setScheduler((Procedure)newValue);
				return;
			case IrPackage.ACTION__TAG:
				setTag((Tag)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.ACTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.ACTION__BODY:
				setBody((Procedure)null);
				return;
			case IrPackage.ACTION__INPUT_PATTERN:
				setInputPattern((Pattern)null);
				return;
			case IrPackage.ACTION__OUTPUT_PATTERN:
				setOutputPattern((Pattern)null);
				return;
			case IrPackage.ACTION__PEEK_PATTERN:
				setPeekPattern((Pattern)null);
				return;
			case IrPackage.ACTION__SCHEDULER:
				setScheduler((Procedure)null);
				return;
			case IrPackage.ACTION__TAG:
				setTag((Tag)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Procedure getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern getInputPattern() {
		return inputPattern;
	}

	@Override
	public String getName() {
		if (tag.isEmpty()) {
			return body.getName();
		} else {
			String str = "";
			for (int i = 0; i < tag.size() - 1; i++) {
				str += tag.get(i) + "_";
			}

			str += tag.get(tag.size() - 1);
			return str;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern getOutputPattern() {
		return outputPattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern getPeekPattern() {
		return peekPattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Procedure getScheduler() {
		return scheduler;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Tag getTag() {
		return tag;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(Procedure newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setInputPattern(Pattern newInputPattern) {
		if (newInputPattern != inputPattern) {
			NotificationChain msgs = null;
			if (inputPattern != null)
				msgs = ((InternalEObject)inputPattern).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__INPUT_PATTERN, null, msgs);
			if (newInputPattern != null)
				msgs = ((InternalEObject)newInputPattern).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__INPUT_PATTERN, null, msgs);
			msgs = basicSetInputPattern(newInputPattern, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__INPUT_PATTERN, newInputPattern, newInputPattern));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputPattern(Pattern newOutputPattern) {
		if (newOutputPattern != outputPattern) {
			NotificationChain msgs = null;
			if (outputPattern != null)
				msgs = ((InternalEObject)outputPattern).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__OUTPUT_PATTERN, null, msgs);
			if (newOutputPattern != null)
				msgs = ((InternalEObject)newOutputPattern).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__OUTPUT_PATTERN, null, msgs);
			msgs = basicSetOutputPattern(newOutputPattern, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__OUTPUT_PATTERN, newOutputPattern, newOutputPattern));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setPeekPattern(Pattern newPeekPattern) {
		if (newPeekPattern != peekPattern) {
			NotificationChain msgs = null;
			if (peekPattern != null)
				msgs = ((InternalEObject)peekPattern).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__PEEK_PATTERN, null, msgs);
			if (newPeekPattern != null)
				msgs = ((InternalEObject)newPeekPattern).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__PEEK_PATTERN, null, msgs);
			msgs = basicSetPeekPattern(newPeekPattern, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__PEEK_PATTERN, newPeekPattern, newPeekPattern));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setScheduler(Procedure newScheduler) {
		if (newScheduler != scheduler) {
			NotificationChain msgs = null;
			if (scheduler != null)
				msgs = ((InternalEObject)scheduler).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__SCHEDULER, null, msgs);
			if (newScheduler != null)
				msgs = ((InternalEObject)newScheduler).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__SCHEDULER, null, msgs);
			msgs = basicSetScheduler(newScheduler, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__SCHEDULER, newScheduler, newScheduler));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setTag(Tag newTag) {
		if (newTag != tag) {
			NotificationChain msgs = null;
			if (tag != null)
				msgs = ((InternalEObject)tag).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__TAG, null, msgs);
			if (newTag != null)
				msgs = ((InternalEObject)newTag).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTION__TAG, null, msgs);
			msgs = basicSetTag(newTag, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTION__TAG, newTag, newTag));
	}
	
	@Override
	public String toString() {
		return getName();
	}

} // ActionImpl
