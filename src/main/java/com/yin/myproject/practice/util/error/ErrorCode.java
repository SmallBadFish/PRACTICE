package com.yin.myproject.practice.util.error;

public enum ErrorCode {

	AUDIT_TYPE_NULL("AUDIT_TYPE_NULL", "δѡ�����״̬"),

	USER_UNLOGIN("USER_UNLOGIN", "�û�δ��¼,�����ӳ�ʱ"),

	BAD_REQUEST("BAD_REQUEST", "�������ݲ�����"),

	DATA_CONFLICT("DATA_CONFLICT", "�����ظ�"),

	USER_PHONE_CONFLICT("USER_PHONE_CONFLICT", "�û��绰�ظ�"),

	USER_NAME_CONFLICT("USER_NAME_CONFLICT", "�û����ظ�"),

	ERR_SERVER_EXCEPTION("ERR_SERVER_EXCEPTION", "�Բ���,��������æ,���Ժ�����"),

	ERR_MISS_ENTITY("ERR_MISS_ENTITY", "����ȱʧ"),

	ERR_CREATE_ENTITY("ERR_CREATE_ENTITY", "���ܴ������ݶ���"),

	NULL_ACCOUNT("NULL_ACCOUNT", "û�е�¼��Ϣ"),

	NOT_FIND_ACCOUNT("NOT_FIND_ACCOUNT", "�û�������"),

	PASSORD_ERROR("PASSORD_ERROR", "�������"),

	VERIFY_FAIL("VERIFY_FAIL", "��֤����ǩ������"),

	MAPPER_ERROR("MAPPER_ERROR", "����ת������"),

	NULL_PAYCOMPANY("NULL_PAYCOMPANY", "û�д���֧����˾����"),

	NULL_AMOUNT("NULL_AMOUNT", "���Ϊ��"),

	NOT_FOUND_RECORD("NOT_FOUND_RECORD", "û���ҵ���Ӧ�����ݼ�¼"),

	SAVE_FAIL("SAVE_FAIL", "��������ʧ��"),

	MAX_ADUIT_AMOUNT("MAX_ADUIT_AMOUNT", "������˶�Ȳ��ô���1000��"),

	CREDIT_AMOUNT("CREDIT_AMOUNT", "���ö�Ȳ���"),

	NOT_ALLOW_LOAN("NOT_ALLOW_LOAN", "������δ��ɵĽ�Ŀǰ������Ͷ��"),

	NOT_ALLOW_AUDIT("NOT_ALLOW_AUDIT", "��������˸ñ�"),

	PAYCOMPANY_ERROR("PAYCOMPANY_ERROR", "����֧����˾����"),

	IMAGE_VALIDATOR_ERROR("NOT_ACCEPTABLE", "��֤�����"),

	PHONE_VALIDATOR_ERROR("PHONE_VALIDATOR_ERROR", "�ֻ���֤�����"),

	ACCOUNT_NOT_ENOUGH("ACCOUNT_NOT_ENOUGH", "�ʻ�����"),

	ERR_ACCOUNT_FROZEN("ERR_ACCOUNT_FROZEN", "�ʻ�������"),

	NULL_INVEST_BEGIN_LOAN("NULL_INVEST_BEGIN_LOAN", "δ�ҵ�Ͷ�ʼ�¼"),

	NO_DATA_AVAILABLE("NO_DATA_AVAILABLE", "����Ч����"),

	SUBTRACT_AMOUNT_ERROR("SUBTRACT_AMOUNT_ERROR", "�����ʽ����ʵ���ʽ�"),

	ERR_DOMAIN_BUILD("ERR_DOMAIN_BUILD", "��ģ����װ�쳣"),

	USER_CONFIRMED_ERROR("USER_CONFIRMED_ERROR", "�û�����֤"),

	USER_UNCONFIRMED_ERROR("USER_UNCONFIRMED_ERROR", "�û�δʵ����֤"),

	UNLOCK_MONEY_ERROR("UNLOCK_MONEY_ERROR", "�����������������"),

	ERR_USER_GETBYPHONE_NOT_FOUND("ERR_USER_GETBYPHONE_NOT_FOUND", "û�з��ָõ绰������û�"),

	ERR_APPCODE_MISSING("ERR_APPCODE_MISSING", "ע���붪ʧ"),

	ERR_MESSAGENO_MISSING("ERR_MESSAGENO_MISSING", "��Ϣ�붪ʧ"),

	ERR_MESSAGEBODY_MISSING("ERR_MESSAGEBODY_MISSING", "��Ϣ�嶪ʧ"),

	ERR_APPCODE_VALUE("ERR_APPCODE_VALUE", "ע���벻��P2P"),

	ERR_BINDINGLOAN_MISSING("ERR_BINDINGLOAN_MISSING", "�������Ϊ��"),

	ERR_SERVICE_NOT_FOUND("ERR_SERVICE_NOT_FOUND", "�渶��û�и��û���Ϣ"),

	ERR_FULLNAME_VALUE("ERR_FULLNAME_VALUE", "�û����Ʋ�����"),

	ERR_PHONE_VALUE("ERR_PHONE_VALUE", "�û��绰���벻����"),

	ERR_IDCARDCODE_VALUE("ERR_IDCARDCODE_VALUE", "���֤�д���"),

	ERR_USER_GETBYID_NOT_FOUND("ERR_USER_GETBYID_NOT_FOUND", "û�з��ָ��û�"),

	ERR_VERIFYCODE_NOT_FOUND("ERR_VERIFYCODE_NOT_FOUND", "�û���֤��û����"),

	ERR_CARD_GETBYUSERID_NOT_FOUND("ERR_CARD_GETBYUSERID_NOT_FOUND", "û�øõ渶�����û���Ϣ"),

	ERR_VERIFYCODE_VALUE("ERR_VERIFYCODE_VALUE", "��֤�����"),

	ERR_PASSWORD_VALUE("ERR_PASSWORD_VALUE", "�û��������"),

	ERR_USER_BINDED_FOR_KYPAY("ERR_USER_BINDED_FOR_KYPAY", "P2P����渶���û���Ϣʧ�ܣ�"),

	ALREADY_CREDITING_ERROR("ALREADY_CREDITING_ERROR", "�Ѿ���������"),

	ALREADY_CREDITED_ERROR("ALREADY_CREDITED_ERROR", "�Ѿ����ųɹ�"),

	ALREADY_INVITE("ALREADY_INVITE", "�Ѿ�������"),

	DECODE_ERROR("DECODE_ERROR", "�����쳣"),

	NOT_USER_ERROR("NOT_USER_ERROR", "û������û�"),

	SAME_REPSD_ERROR("SAME_REPSD_ERROR", "���������ȷ�����벻��ͬ"),

	INVITECODE_ERROR("INVITECODE_ERROR", "�������쳣"),

	KYPAY_ACCOUNT_CONFLIECT("KYPAY_ACCOUNT_CONFLIECT", "ִ�����ʻ��Ѱ�"),

	KYPAY_AKKA_ERROR("KYPAY_ERROR", "���ӵ渶������"),

	KYPAY_ERROR("KYPAY_ERROR", "�渶����������"),

	KYPAY_COUNT_NOT_FOUND("KYPAY_COUNT_NOT_FOUND", "�渶���˻�������"),

	SIGN_CONTRACT_ERROR("SIGN_CONTRACT_ERROR", "�渶��δǩԼ"),

	SAME_OLDEMAIL_ERROR("SAME_OLDEMAIL_ERROR", "�����ظ�"),

	SAME_OLDPSD_ERROR("SAME_OLDPSD_ERROR", "ԭʼ������������ظ�"),

	IDENTITY_ERROR("IDENTITY_ERROR", "���û��޴���ݷ���"),

	OLDPSD_ERROR("OLDPSD_ERROR", "ԭ�������"),

	LOAN_STATUS_ERROR("LOAN_STATUS_ERROR", "���״̬����ȷ,����ϵϵͳ����Ա���Ժ�����"),

	HOLD_AMOUNT_NOT_ENOUGH("HOLD_AMOUNT_NOT_ENOUGH", "�����쳣,�����еĶ�Ȳ���,������"),

	ASSIGNMENT_IS_TRANSFERING("ASSIGNMENT_IS_TRANSFERING", "����ծȯ���ڽ�����,���Ժ�����"),

	BUYER_AND_SELLER_IS_SAME_PERSON("BUYER_AND_SELLER_IS_SAME_PERSON", "���ܹ����Լ���ծȯ"),

	NO_POWER_BUY_ASSIGNMENT("NO_POWER_BUY_ASSIGNMENT", "���ܹ����ծȯ"),

	UN_SUPPORT_BUY_LEFT_AMOUNT("UN_SUPPORT_BUY_LEFT_AMOUNT", "��֧������"),

	NOT_SUFFICIENT_FUNDS("NOT_SUFFICIENT_FUNDS", "�������ծȨ��Ȳ���"),

	ERR_SERVICEACCOUNT_NOT_COMPANY("ERR_SERVICEACCOUNT_NOT_COMPANY", "���û����ǵ渶������ҵ�û�"),

	KYPAY_LOCKED("KYPAY_LOCKED", "��P2P�����������û�з���ֵ����ʧ��"),

	WITHDRAWAL_AMOUNT_ERROR("WITHDRAWAL_AMOUNT_ERROR", "���ֽ�� <=0,�渶����Լ�����ֶ��"),

	ERR_UPDATE_ACCOUNT("ERR_UPDATE_ACCOUNT", "�޸ĵ渶���˻�ʧ��"),

	ERR_USER_EXIST("ERR_USER_EXIST", "�õ渶���ѱ�ǩԼ"),

	ERR_DB_LOANCONFIRM_MISS("ERR_DB_LOANCONFIRM_MISS", "���ݿ��в���������"),

	ERR_VERISION_NOT_EQUAL("ERR_VERISION_NOT_EQUAL", "����İ汾��Ϣ��ƥ��"),

	ERRORCODE_NOT_EXIST("ERRORCODE_ERROR", "���״��д����벻����"),

	ERR_CREATE_KYLINKER("ERR_CREATE_KYLINKER", "����KyLinker��ʧ��"),

	ALREADY_OPERATE_TASK_ERROR("ALREADY_OPERATE_TASK_ERROR", "�������ѱ�����"),

	ERR_DB_ACCOUNT_CARD_NOT_EXIT("ERR_DB_ACCOUNT_CARD_NOT_EXIT", "���û���Ƿ��渶���˻�������"),

	ERR_DB_KYLOANHISTORY_NOT_EXIT("ERR_DB_KYLOANHISTORY_NOT_EXIT", "���û���������ʷ�����ڵ渶��1��2��3"),

	NETWORK_ERROR("NETWORK_ERROR", "��������������");

	private String code;
	private String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ErrorCode{code=" + code + ", message='" + message + "'} ";
	}

}
