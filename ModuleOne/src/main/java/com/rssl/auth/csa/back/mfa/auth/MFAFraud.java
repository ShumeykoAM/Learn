package com.rssl.auth.csa.back.mfa.auth;

import com.rssl.auth.csa.back.mfanew.fraud.MFAFraudPreprocessorResolutions;

import java.util.Map;

/**
 * Блок, описывающий обработку резолюции АС ФМ.
 * @author Alexander Shumeyko
 * @ created 23.07.2019
 */
public class MFAFraud
{
	private boolean using;
	private MFAFraudMessage message;
	private Map<MFAFraudPreprocessorResolutions, MFAFraudPreprocessorData> preprocessor;

	/**
	 * @return Признак использования интеграции с АС ФМ	Для значения false используются настройки из блока preprocessor.NA
	 */
	public boolean isUsing()
	{
		return using;
	}

	public void setUsing(boolean using)
	{
		this.using = using;
	}

	/**
	 * @return Описание системыв оповещения АС ФМ
	 */
	public MFAFraudMessage getMessage()
	{
		return message;
	}

	public void setMessage(MFAFraudMessage message)
	{
		this.message = message;
	}

	/**
	 * @return Описание правил обработки резолюции АС ФМ
	 */
	public Map<MFAFraudPreprocessorResolutions, MFAFraudPreprocessorData> getPreprocessor()
	{
		return preprocessor;
	}

	public void setPreprocessor(Map<MFAFraudPreprocessorResolutions, MFAFraudPreprocessorData> preprocessor)
	{
		this.preprocessor = preprocessor;
	}
}
