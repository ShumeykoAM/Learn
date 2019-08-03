package com.rssl.auth.csa.back.mfa.auth;

import com.rssl.auth.csa.back.mfa.IdentifierType;

import java.util.List;

/**
 * Схема для канала для аутентифицируемых операций физиков
 * @author Alexander Shumeyko
 * @ created 23.07.2019
 */
public class MFAChannel
{
	private MFAFraud fraud;
	private List<MFAIdentifier> identifiers;

	/**
	 * @return Блок, описывающий обработку резолюции АС ФМ.
	 */
	public MFAFraud getFraud()
	{
		return fraud;
	}

	public void setFraud(MFAFraud fraud)
	{
		this.fraud = fraud;
	}

	/**
	 * @return Информация о доступных идентификаторах
	 */
	public List<MFAIdentifier> getIdentifiers()
	{
		return identifiers;
	}

	public void setIdentifiers(List<MFAIdentifier> identifiers)
	{
		this.identifiers = identifiers;
	}

	/**
	 * Возвращает идентификатор из списка идентификаторов, если нет такого идентификатора то null
	 * @param identifierType Тип искомого идентификатора
	 * @return Идентификатор
	 */
	public MFAIdentifier getIdentifier(IdentifierType identifierType)
	{
		for (MFAIdentifier identifier : identifiers)
		{
			if (identifier.getIdentifier() == identifierType)
				return identifier;
		}
		return null;
	}
}
