package com.rssl.auth.csa.back.mfa.auth;

import com.rssl.auth.csa.back.mfa.AdditionalAuthenticationMode;
import com.rssl.auth.csa.back.mfa.IdentifierType;

import java.util.List;

/**
 * Информация о доступном идентификаторе
 * @author Alexander Shumeyko
 * @ created 23.07.2019
 */
public class MFAIdentifier
{
	private IdentifierType identifier;
	private AdditionalAuthenticationMode secondStepMode;
	private List<MFAStep> steps;

	/**
	 * @return Тип идентификатора
	 */
	public IdentifierType getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(IdentifierType identifier)
	{
		this.identifier = identifier;
	}

	/**
	 * @return Тип дополнительного подтверждения
	 */
	public AdditionalAuthenticationMode getSecondStepMode()
	{
		return secondStepMode;
	}

	public void setSecondStepMode(AdditionalAuthenticationMode secondStepMode)
	{
		this.secondStepMode = secondStepMode;
	}

	/**
	 * @return Список шагов подтверждения
	 */
	public List<MFAStep> getSteps()
	{
		return steps;
	}

	public void setSteps(List<MFAStep> steps)
	{
		this.steps = steps;
	}
}
