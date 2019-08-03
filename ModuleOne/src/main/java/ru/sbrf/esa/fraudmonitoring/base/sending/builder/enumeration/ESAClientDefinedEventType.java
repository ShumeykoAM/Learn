package ru.sbrf.esa.fraudmonitoring.base.sending.builder.enumeration;

/**
 * TODO Description
 * @author kot
 * @ created 03.08.2019
 * @ $Author$
 * @ $Revision$
 */
public enum ESAClientDefinedEventType
{

	SS_WITH_OTP                     ,
	SS_WITHOUT_OTP                  ,
	SS_IDENTIFICATION               ,
	FLA_EMPTY                       ,
	FLA_CLIENT_SUSPICION            ,
	ENROLL                          ,
	LOGIN_PASSWORD                  ,
	NEW_MOB_APPL                    ,
	SS_SUCCESSFUL_AUTHENTICATION    ,
	CD_SUCCESSFUL_AUTHENTICATION    ,
	CD_UNSUCCESSFUL_AUTHENTICATION  ,
	CD_FAILED_FACTOR_ATTEMPT        ,
	UU_PROFILE_MERGING              ,
	AUTH_FACTOR_CHANGE              ,
	CHANGE_PASSWORD                 ,
	CHANGE_LOGIN_ID                 ,
	NEW_BIOM_TEMP                   ,
	UU_NEW_AUTH_FACTOR              ,
	UU_DEL_AUTH_FACTOR              ,
	UU_UPD_AUTH_FACTOR              ,
	UU_NEW_AUTH_FACTOR_SUCCESS      ,
	UU_DEL_AUTH_FACTOR_SUCCESS      ,
	UU_UPD_AUTH_FACTOR_SUCCESS      ,
	UU_NEW_AUTH_FACTOR_UNSUCCESS    ,
	UU_DEL_AUTH_FACTOR_UNSUCCESS    ,
	UU_UPD_AUTH_FACTOR_UNSUCCESS
}
