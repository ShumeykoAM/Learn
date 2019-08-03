package com.rssl.auth.csa.back.mfa.auth;

import ru.sbrf.esa.fraudmonitoring.base.sending.builder.enumeration.ESAClientDefinedEventType;

/**
 * Описание системы оповещения АС ФМ
 * @author Alexander Shumeyko
 * @ created 23.07.2019
 */
public class MFAFraudMessage
{
	private MFAFraudMessageFail fail;
	private MFAFraudMessageSuccessRequest successRequest;
	private ESAClientDefinedEventType successNotify;

	/**
	 * @return Ошибка предъявления фактора
	 */
	public MFAFraudMessageFail getFail()
	{
		return fail;
	}

	public void setFail(MFAFraudMessageFail fail)
	{
		this.fail = fail;
	}

	/**
	 * @return Успешное предъявление фактора
	 */
	public MFAFraudMessageSuccessRequest getSuccessRequest()
	{
		return successRequest;
	}

	public void setSuccessRequest(MFAFraudMessageSuccessRequest successRequest)
	{
		this.successRequest = successRequest;
	}

	/**
	 * @return Оповещение об успешном предъявлении фактора, затребованном АС ФМ в резолюции REVIEW
	 */
	public ESAClientDefinedEventType getSuccessNotify()
	{
		return successNotify;
	}

	public void setSuccessNotify(ESAClientDefinedEventType successNotify)
	{
		this.successNotify = successNotify;
	}
}
