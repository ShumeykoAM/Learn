package com.rssl.auth.csa.back.mfa.auth;

import ru.sbrf.esa.fraudmonitoring.base.sending.builder.enumeration.ESAClientDefinedEventType;

/**
 * Ошибка предъявления фактора
 * @author Alexander Shumeyko
 * @ created 23.07.2019
 */
public class MFAFraudMessageFail
{
	ESAClientDefinedEventType factor;
	ESAClientDefinedEventType suspicion;

	/**
	 * @return Некоррекное предъявление фактора клиентом
	 */
	public ESAClientDefinedEventType getFactor()
	{
		return factor;
	}

	public void setFactor(ESAClientDefinedEventType factor)
	{
		this.factor = factor;
	}

	/**
	 * @return Клиент подозревает фрод (нажал кнопку "Это не я" при использовании QR)
	 */
	public ESAClientDefinedEventType getSuspicion()
	{
		return suspicion;
	}

	public void setSuspicion(ESAClientDefinedEventType suspicion)
	{
		this.suspicion = suspicion;
	}
}
