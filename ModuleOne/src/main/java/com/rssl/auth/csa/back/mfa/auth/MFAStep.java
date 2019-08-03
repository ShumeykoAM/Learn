package com.rssl.auth.csa.back.mfa.auth;

import com.rssl.phizic.common.types.multi.FactorType;

import java.util.List;

/**
 * Шаг подтверждения
 * @author Alexander Shumeyko
 * @ created 23.07.2019
 */
public class MFAStep
{
	private int              stepNumber;
	private List<FactorType> availableFactors;

	/**
	 * @return номер шага
	 */
	public int getStepNumber()
	{
		return stepNumber;
	}

	public void setStepNumber(int stepNumber)
	{
		this.stepNumber = stepNumber;
	}

	/**
	 * @return Список доступных факторов для данного шага, достаточно подтвердить один из них и шаг считается пройденным
	 */
	public List<FactorType> getAvailableFactors()
	{
		return availableFactors;
	}

	public void setAvailableFactors(List<FactorType> availableFactors)
	{
		this.availableFactors = availableFactors;
	}
}
