package com.rssl.auth.csa.back.mfanew.fraud;

/**
 * TODO Description
 * @author kot
 * @ created 03.08.2019
 * @ $Author$
 * @ $Revision$
 */
public enum MFAFraudPreprocessorResolutions
{
	ALLOW,
	REVIEW_WITH_AF,
	REVIEW_WITHOUT_AF,
	DENY,
	BLOCK,
	NA;
}
