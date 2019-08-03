package com.rssl.auth.csa.back.mfa.auth;

import com.rssl.auth.csa.back.mfa.uapi.UAPIErrors;
import ru.sbrf.esa.fraud.common.FraudVerdictType;

/**
 * Данные для реакции на резолюцию
 * @author Alexander Shumeyko
 * @ created 23.07.2019
 */
public class MFAFraudPreprocessorData
{
	private FraudVerdictType resolution;
	private UAPIErrors error;
	private String textKey;
	private String text;

	/**
	 * @return Правило работы алгоритма при отсутствии ответа АС ФМ или отключения интеграции (полное / для канала / для операции)
	 */
	public FraudVerdictType getResolution()
	{
		return resolution;
	}

	public void setResolution(FraudVerdictType resolution)
	{
		this.resolution = resolution;
	}

	/**
	 * @return код ошибки uAPI
	 */
	public UAPIErrors getError()
	{
		return error;
	}

	public void setError(UAPIErrors error)
	{
		this.error = error;
	}

	/**
	 * @return Код (key) текстового сообщения из таблицы STATIC_MESSAGES
	 */
	public String getTextKey()
	{
		return textKey;
	}

	public void setTextKey(String textKey)
	{
		this.textKey = textKey;
	}

	/**
	 * @return Текст ошибки	Если текст не задан, но задано textKey, то выполняется загрузка из STATIC_MESSAGES.TEXT по STATIC_MESSAGES.KEY=textKey
	 */
	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}
}
