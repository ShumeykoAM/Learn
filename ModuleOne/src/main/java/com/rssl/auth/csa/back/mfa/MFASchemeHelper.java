package com.rssl.auth.csa.back.mfa;


import com.rssl.auth.csa.back.exceptions.multi.MFASchemeException;
import com.rssl.auth.csa.back.mfa.auth.MFAChannel;
import org.apache.commons.collections4.CollectionUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import java.io.Reader;

/**
 * Класс-хелпер для обработки схем МФА
 */
public final class MFASchemeHelper
{
	private static final MFASchemeHelper INSTANCE       = new MFASchemeHelper();
	private static final String          MES_SCHEME_NOT_FOUND = "Настройка схем МФА не найдена";

	/**
	 * ctor
	 */
	private MFASchemeHelper()
	{}

	/**
	 * Получение экземпляра класса хэлпера
	 * @return экземпляр класса хэлпера
	 */
	public static MFASchemeHelper getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Парсинг настроек схем МФА
	 * @param yamlReader данные в формате YAML
	 * @param classOfMfaStructure класс, описывающий объект структуры схемы МФА
	 * @return объект структуры схемы МФА
	 */
	public <T> T parseMFASettings(Reader yamlReader, Class<T> classOfMfaStructure)
	{
		Yaml yaml = new Yaml(new Constructor(classOfMfaStructure));
		return yaml.load(yamlReader);
	}

	/**
	 * Получить настройки схем МФА из кэша
	 * @return настройки схем МФА
	 */
	public MFAChannel getCachedMFASettings() throws MFASchemeException
	{
		MFAChannel mfaSettings;
		try
		{
			mfaSettings = parseMFASettings(getMFASettingsFromDB(), MFAChannel.class);
			validateScheme(mfaSettings);
		}
		catch (YAMLException e)
		{
			throw new MFASchemeException(MES_SCHEME_NOT_FOUND, e);
		}
		catch (Exception e)
		{
			throw new MFASchemeException(MES_SCHEME_NOT_FOUND, e);
		}
		return mfaSettings;
	}

	/**
	 * Получить настройки схем МФА из БД
	 * @return данные в формате YAML
	 * @throws Exception ошибка получения данных
	 */
	private Reader getMFASettingsFromDB() throws Exception
	{

		return null;
	}


	private void validateScheme(MFAChannel scheme) throws MFASchemeException
	{
		if (scheme.getFraud() == null)
			throw new MFASchemeException("Схема не валидна, блок fraud должен быть задан!");

		if (CollectionUtils.isEmpty(scheme.getIdentifiers()))
			throw new MFASchemeException("Схема не валидна, должен быть задан список identifiers, содержащий хотябы один шаг!");

		if (scheme.getFraud().getMessage() == null)
			throw new MFASchemeException("Схема не валидна, блок fraud.message должен быть задан!");

		if (scheme.getFraud().getMessage().getFail() == null)
			throw new MFASchemeException("Схема не валидна, блок fraud.message.fail должен быть задан!");

		if (scheme.getFraud().getMessage().getSuccessRequest() == null)
			throw new MFASchemeException("Схема не валидна, блок fraud.message должен быть задан!");
	}
}
