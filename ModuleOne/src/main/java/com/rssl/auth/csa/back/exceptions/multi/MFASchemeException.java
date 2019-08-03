package com.rssl.auth.csa.back.exceptions.multi;

/**
 * Ошибки использования схем MFA
 * @author Alexander Shumeyko
 * @ created 30.01.2019
 */
public class MFASchemeException extends Exception
{
	/**
	 * ctor
	 * @param message Сообщение об ошибке
	 */
	public MFASchemeException(final String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param message Сообщение об ошибке
	 * @param cause Причина возникновения исключительной ситуации
	 */
	public MFASchemeException(final String message, Exception cause)
	{
		super(message, cause);
	}
}
