package com.rssl.auth.csa.back.mfa;

import com.rssl.auth.csa.back.exceptions.multi.MFASchemeException;
import com.rssl.auth.csa.back.mfa.auth.*;
import com.rssl.auth.csa.back.mfa.uapi.UAPIErrors;
import com.rssl.auth.csa.back.mfanew.fraud.MFAFraudPreprocessorResolutions;
import com.rssl.phizic.common.types.multi.FactorType;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.sbrf.esa.fraud.common.FraudVerdictType;
import ru.sbrf.esa.fraudmonitoring.base.sending.builder.enumeration.ESAClientDefinedEventType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Тест валидатора схемы аутентификации
 * @author kot
 * @ created 03.08.2019
 * @ $Author$
 * @ $Revision$
 */
public class MFASchemeHelperTest
{
	private static Method validate;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@BeforeClass
	public static void prepareTestClass() throws NoSuchMethodException
	{
		validate = MFASchemeHelper.getInstance().getClass().getDeclaredMethod("validateScheme", MFAChannel.class);
		validate.setAccessible(true);
	}

	/**
	 * Схема валидна
	 */
	@Test
	public void test1() throws Throwable
	{
		MFAChannel channel = getValidScheme();
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Отсутствует блок fraud
	 */
	@Test
	public void test2() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.setFraud(null);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Отсутствует блок список identifiers
	 */
	@Test
	public void test3() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.setIdentifiers(null);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Блок список identifiers - пустой список
	 */
	@Test
	public void test4() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.setIdentifiers(new LinkedList<>());
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Блок fraud.message должен быть задан
	 */
	@Test
	public void test5() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.getFraud().setMessage(null);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Блок fraud.message.fail должен быть
	 */
	@Test
	public void test6() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.getFraud().getMessage().setFail(null);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Блок fraud.message.successRequest должен быть
	 */
	@Test
	public void test7() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.getFraud().getMessage().setSuccessRequest(null);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Блок fraud.message.successNotify должен быть
	 */
	@Test
	public void test8() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.getFraud().getMessage().setSuccessNotify(null);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Блок fraud.message.fail.factor должен быть
	 */
	@Test
	public void test9() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.getFraud().getMessage().getFail().setFactor(null);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Блок fraud.message.successRequest.withAF должен быть
	 */
	@Test
	public void test10() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.getFraud().getMessage().getSuccessRequest().setWithAF(null);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Блок fraud.message.successRequest.withoutAF должен быть
	 */
	@Test
	public void test11() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.getFraud().getMessage().getSuccessRequest().setWithoutAF(null);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Блок мапа fraud.preprocessor должен быть
	 */
	@Test
	public void test12() throws Throwable
	{
		thrown.expect(MFASchemeException.class);

		MFAChannel channel = getValidScheme();
		channel.getFraud().setPreprocessor(null);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Если fraud.using = false, то мапа fraud.preprocessor должна содержать MFAFraudPreprocessorResolutions.NA
	 *      остальные резолюции препроцессора не нужны, так как при fraud.using = false мы всегда используем
	 *      блок MFAFraudPreprocessorResolutions.NA, а во фрод не ходим
	 */
	@Test
	public void test13() throws Throwable
	{
		thrown.expect(MFASchemeException.class);
		MFAChannel channel = getValidScheme();
		channel.getFraud().setUsing(false);
		channel.getFraud().getPreprocessor().remove(MFAFraudPreprocessorResolutions.NA);
		validateScheme(channel);
	}

	/**
	 * Схема валидна
	 * Если fraud.using = false, то мапа fraud.preprocessor не обязана содержать
	 *          MFAFraudPreprocessorResolutions.ALLOW
	 *          MFAFraudPreprocessorResolutions.REVIEW_WITH_AF
	 *          MFAFraudPreprocessorResolutions.REVIEW_WITHOUT_AF
	 *          MFAFraudPreprocessorResolutions.DENY
	 *          MFAFraudPreprocessorResolutions.BLOCK
	 */
	@Test
	public void test14() throws Throwable
	{
		MFAChannel channel = getValidScheme();
		channel.getFraud().setUsing(false);
		channel.getFraud().getPreprocessor().remove(MFAFraudPreprocessorResolutions.ALLOW);
		channel.getFraud().getPreprocessor().remove(MFAFraudPreprocessorResolutions.REVIEW_WITH_AF);
		channel.getFraud().getPreprocessor().remove(MFAFraudPreprocessorResolutions.REVIEW_WITHOUT_AF);
		channel.getFraud().getPreprocessor().remove(MFAFraudPreprocessorResolutions.DENY);
		channel.getFraud().getPreprocessor().remove(MFAFraudPreprocessorResolutions.BLOCK);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Если fraud.using = true, то мапа fraud.preprocessor должна содержать MFAFraudPreprocessorResolutions.ALLOW
	 */
	@Test
	public void test15() throws Throwable
	{
		thrown.expect(MFASchemeException.class);
		MFAChannel channel = getValidScheme();
		channel.getFraud().setUsing(true);
		channel.getFraud().getPreprocessor().remove(MFAFraudPreprocessorResolutions.ALLOW);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Если fraud.using = true и identifiers[любой из списка].secondStepMode = NONE, то мапа fraud.preprocessor должна содержать MFAFraudPreprocessorResolutions.REVIEW_WITHOUT_AF
	 */
	@Test
	public void test16() throws Throwable
	{
		thrown.expect(MFASchemeException.class);
		MFAChannel channel = getValidScheme();
		channel.getFraud().setUsing(true);

		//Зададим только один идентификатор
		List<MFAIdentifier> identifiers = new LinkedList<>();
		identifiers.add(new MFAIdentifierEx(IdentifierType.XSID, AdditionalAuthenticationMode.NONE, new MFAStepEx(1, FactorType.PIN_SRP, FactorType.PUSH)));
		channel.setIdentifiers(identifiers);

		channel.getFraud().getPreprocessor().remove(MFAFraudPreprocessorResolutions.REVIEW_WITHOUT_AF);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Если fraud.using = true и identifiers[любой из списка].secondStepMode = STATIC, то мапа fraud.preprocessor должна содержать MFAFraudPreprocessorResolutions.REVIEW_WITHOUT_AF
	 */
	@Test
	public void test17() throws Throwable
	{
		thrown.expect(MFASchemeException.class);
		MFAChannel channel = getValidScheme();
		channel.getFraud().setUsing(true);

		//Зададим только один идентификатор
		List<MFAIdentifier> identifiers = new LinkedList<>();
		identifiers.add(new MFAIdentifierEx(IdentifierType.XSID, AdditionalAuthenticationMode.STATIC, new MFAStepEx(1, FactorType.PIN_SRP, FactorType.PUSH)));
		channel.setIdentifiers(identifiers);

		channel.getFraud().getPreprocessor().remove(MFAFraudPreprocessorResolutions.REVIEW_WITHOUT_AF);
		validateScheme(channel);
	}

	/**
	 * Схема не валидна
	 * Если fraud.using = true и identifiers[любой из списка].secondStepMode = STATIC, то мапа fraud.preprocessor должна содержать MFAFraudPreprocessorResolutions.REVIEW_WITH_AF
	 */
	@Test
	public void test18() throws Throwable
	{
		thrown.expect(MFASchemeException.class);
		MFAChannel channel = getValidScheme();
		channel.getFraud().setUsing(true);

		//Зададим только один идентификатор
		List<MFAIdentifier> identifiers = new LinkedList<>();
		identifiers.add(new MFAIdentifierEx(IdentifierType.XSID, AdditionalAuthenticationMode.FRAUD, new MFAStepEx(1, FactorType.PIN_SRP, FactorType.PUSH)));
		channel.setIdentifiers(identifiers);

		channel.getFraud().getPreprocessor().remove(MFAFraudPreprocessorResolutions.REVIEW_WITH_AF);
		validateScheme(channel);
	}


	//Вызываем private метод валидации и пробрасываем реальное исключение этого метода, если оно возникло
	private void validateScheme(MFAChannel scheme) throws Throwable
	{
		try
		{
			validate.invoke(MFASchemeHelper.getInstance(), scheme);
		}
		catch (InvocationTargetException e)
		{
			throw e.getCause() != null ? e.getCause() : e;
		}
	}

	//Наиболее полный вариант валидной схемы
	private MFAChannel getValidScheme()
	{
		MFAFraudMessageFail fraudMessageFail = new MFAFraudMessageFail();
		fraudMessageFail.setFactor(ESAClientDefinedEventType.FLA_EMPTY);
		fraudMessageFail.setSuspicion(ESAClientDefinedEventType.FLA_CLIENT_SUSPICION);

		MFAFraudMessageSuccessRequest mfaFraudMessageSuccessRequest = new MFAFraudMessageSuccessRequest();
		mfaFraudMessageSuccessRequest.setWithAF(ESAClientDefinedEventType.SS_WITH_OTP);
		mfaFraudMessageSuccessRequest.setWithoutAF(ESAClientDefinedEventType.SS_WITHOUT_OTP);

		MFAFraudMessage message = new MFAFraudMessage();
		message.setFail(fraudMessageFail);
		message.setSuccessRequest(mfaFraudMessageSuccessRequest);
		message.setSuccessNotify(ESAClientDefinedEventType.SS_SUCCESSFUL_AUTHENTICATION);

		Map<MFAFraudPreprocessorResolutions, MFAFraudPreprocessorData> preprocessor = new HashMap<>();
		preprocessor.put(MFAFraudPreprocessorResolutions.ALLOW, new MFAFraudPreprocessorDataEx(FraudVerdictType.ALLOW));
		preprocessor.put(MFAFraudPreprocessorResolutions.REVIEW_WITH_AF, new MFAFraudPreprocessorDataEx(FraudVerdictType.REVIEW, "Нужно проверить еще один фактор"));
		preprocessor.put(MFAFraudPreprocessorResolutions.REVIEW_WITHOUT_AF, new MFAFraudPreprocessorDataEx(FraudVerdictType.DENY, UAPIErrors.SUSPICIOUS_TRANSACTION_DENY, "Выполнение аутентификации невозможно. Попробуйте позднее."));
		preprocessor.put(MFAFraudPreprocessorResolutions.DENY, new MFAFraudPreprocessorDataEx(UAPIErrors.SUSPICIOUS_TRANSACTION_DENY, "Выполнение аутентификации невозможно. Попробуйте позднее."));
		preprocessor.put(MFAFraudPreprocessorResolutions.BLOCK, new MFAFraudPreprocessorDataEx(UAPIErrors.SUSPICIOUS_TRANSACTION_DENY, "Выполнение аутентификации невозможно. Попробуйте позднее."));
		preprocessor.put(MFAFraudPreprocessorResolutions.NA, new MFAFraudPreprocessorDataEx(UAPIErrors.SUSPICIOUS_TRANSACTION_DENY, "Выполнение аутентификации невозможно. Попробуйте позднее."));

		MFAFraud mfaFraud = new MFAFraud();
		mfaFraud.setPreprocessor(preprocessor);
		mfaFraud.setUsing(true);
		mfaFraud.setMessage(message);

		List<MFAIdentifier> identifiers = new LinkedList<>();
		identifiers.add(new MFAIdentifierEx(IdentifierType.FACE, AdditionalAuthenticationMode.STATIC, new MFAStepEx(1, FactorType.PIN_SRP, FactorType.PUSH), new MFAStepEx(2, FactorType.NONE)));
		identifiers.add(new MFAIdentifierEx(IdentifierType.XSID, AdditionalAuthenticationMode.NONE, new MFAStepEx(1, FactorType.PIN_SRP, FactorType.PUSH)));

		MFAChannel scheme = new MFAChannel();
		scheme.setFraud(mfaFraud);
		scheme.setIdentifiers(identifiers);

		return scheme;
	}

	private static class MFAFraudPreprocessorDataEx extends MFAFraudPreprocessorData
	{
		public MFAFraudPreprocessorDataEx(FraudVerdictType resolution, UAPIErrors error, String text)
		{
			setResolution(resolution);
			setError(error);
			setText(text);
		}

		public MFAFraudPreprocessorDataEx(FraudVerdictType resolution)
		{
			setResolution(resolution);
		}

		public MFAFraudPreprocessorDataEx(UAPIErrors error, String text)
		{
			setResolution(FraudVerdictType.DENY);
			setError(error);
			setText(text);
		}

		public MFAFraudPreprocessorDataEx(FraudVerdictType resolution, String text)
		{
			setResolution(resolution);
			setText(text);
		}
	}

	private static class MFAIdentifierEx extends MFAIdentifier
	{
		public MFAIdentifierEx(IdentifierType identifier, AdditionalAuthenticationMode secondStepMode, MFAStep... mfaSteps)
		{
			setIdentifier(identifier);
			setSecondStepMode(secondStepMode);
			setSteps(Arrays.asList(mfaSteps));
		}
	}

	private static class MFAStepEx extends MFAStep
	{
		public MFAStepEx(int stepNumber, FactorType... availableFactors)
		{
			setStepNumber(stepNumber);
			setAvailableFactors(Arrays.asList(availableFactors));
		}
	}
}


