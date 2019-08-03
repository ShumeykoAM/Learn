package com.rssl.auth.csa.back.mfa.auth;

import ru.sbrf.esa.fraudmonitoring.base.sending.builder.enumeration.ESAClientDefinedEventType;

/**
 * Успешное предъявление фактора
 * @author Alexander Shumeyko
 * @ created 23.07.2019
 */
public class MFAFraudMessageSuccessRequest
{
	private ESAClientDefinedEventType withAF;
	private ESAClientDefinedEventType withoutAF;

	/**
	 * @return Успешная проверка аутентификация, у клиента имеется возможность предъявить дополнительный фактор (есть фактор и возможность)
	 */
	public ESAClientDefinedEventType getWithAF()
	{
		return withAF;
	}

	public void setWithAF(ESAClientDefinedEventType withAF)
	{
		this.withAF = withAF;
	}

	/**
	 * @return Успешная проверка аутентификация, у клиента отсутствует возможность предъявить дополнительный фактор
	 */
	public ESAClientDefinedEventType getWithoutAF()
	{
		return withoutAF;
	}

	public void setWithoutAF(ESAClientDefinedEventType withoutAF)
	{
		this.withoutAF = withoutAF;
	}
}
