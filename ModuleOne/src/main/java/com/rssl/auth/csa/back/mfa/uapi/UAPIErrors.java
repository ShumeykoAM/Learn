package com.rssl.auth.csa.back.mfa.uapi;

/**
 * TODO Description
 * @author kot
 * @ created 03.08.2019
 * @ $Author$
 * @ $Revision$
 */
public enum UAPIErrors
{
	INCORRECT_REQUEST                   ,
	NO_SCHEME                           ,
	PARAMETER_MISSING                   ,
	TOO_MANY_PARAM_INSTANCES            ,
	SERVICE_UNAVAILABLE                 ,
	INTERNAL_SERVER_ERROR               ,
	OPERATION_UNAVAILABLE               ,
	CONSENT_REQUIRED                    ,
	UNTRUSTED_CREDENTIALS               ,
	REFUSED_TO_GIVE_CONSENT             ,
	INVALID_CREDENTIALS                 ,
	AUTHENTICATOR_BLOCKED               ,
	AUTHENTICATION_REQUIRED             ,
	DENIED                              ,
	AUTHENTICATOR_IS_BLACKLISTED        ,
	AUTHENTICATOR_IS_REUSED             ,
	AUTHENTICATOR_INVALID               ,
	USER_CHANNEL_BLOCKED                ,
	NO_AUTHENTICATOR                    ,
	AUTHENTICATOR_ALREADY_REGISTERED    ,
	TOO_MANY_AUTHENTICATORS             ,
	ATTEMPTS_LIMIT_REACHED              ,
	INVALID_SAMPLE                      ,
	SAMPLE_ALREADY_REGISTERED           ,
	USER_BLOCKED                        ,
	INITIALIZATION_REQUIRED             ,
	NO_APPROPRIATE_AUTHENTICATORS       ,
	SUSPICIOUS_TRANSACTION_DENY         ,
	SUSPICIOUS_TRANSACTION_BLOCK_USER   ,
	SUSPICIOUS_TRANSACTION_REVIEW       ,
	UNAUTHORIZED                        ;
}
