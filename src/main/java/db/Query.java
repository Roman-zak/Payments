package db;

public class Query {
        public static final String USER_EXIST_BY_LOGIN = "select * from user_entity where email = ?";
        public static final String USER_CREATE = "insert into user values(default,?,?,?,?,?,?)";
        public static final String USER_UPDATE = "update user_entity set name = ?, surname = ?, email = ? , password = ?, role =?, status = ?  where id = ?";
        public static final String USER_DELETE = "delete from user_entity where  id = ?";
        public static final String USER_GET_BY_ID = "select * from user_entity where id= ?";
        public static final String USER_GET_BY_EMAIL = "select * from user where email= ?";
        public static final String USER_GET_ALL = "select * from user";
        public static final String ROLE_GET_BY_NAME = "select * from user_role where name = ?";
        public static final String ROLE_GET_BY_ID = "select * from user_role where id = ?";
        public static final String ROLE_GET_ALL = "select * from user_role";
        public static final String CARD_GET_BY_ID = "select * from card where id = ?";
        public static final String CARD_GET_BY_ACCOUNT_ID = "select * from credit_card where account_id = ?";
        public static final String CARD_GET_ALL = "select * from card";
        public static final String CARD_GET_BY_CARD_NUMBER = "select * from card where card_number = ?";
        public static final String CARD_INSERT = "insert into credit_card values(default,?,?,?,?,?)";
        public static final String CARD_DELETE = "delete from card where id = ?";
        public static final String CARD_UPDATE = "update card set card_number = ?, pin_num = ?, cvv_num = ?, expiry_date = ?, card_type = ? where id = ?";
        public static final String PAYMENT_CREATE = "insert into payment values(default,?,?,?,?,?,default)";
        public static final String ACCOUNT_UNBLOCK_REQUEST_INSERT = "insert into request_account values(default,?)";
        public static final String PAYMENT_GET_BY_ACCOUNT_FROM_ID = "select * from payment where payment_from_account_id = ?";
        public static final String PAYMENT_GET_BY_ACCOUNT_TO_ID = "select * from payment where payment_to_account_id = ?";

        public static final String ACCOUNT_GET_ALL_BY_USER_ID = "select * from account as acc left join user_has_account as uha on acc.id= uha.account_id where uha.user_id = ?";
        public static final String USER_HAS_ACCOUNT_INSERT = "insert into user_has_account values (?,?)";
        public static final String ACCOUNT_INSERT = "insert into account values (default, ?,?,?,?,?,?,?, default)";
        public static final String VAULT = "insert into vault values (default) returning number";
        public static final String ACCOUNT_GET_ALL_BY_USER_EMAIL = "select * from account where user_login like ? ";
        public static final String CARD_GET_ALL_BY_ACCOUNT_NUMBER = "select * from card where account_num = ?";
        public static final String PAYMENT_GET_BY_USER_ID = "select p.id, a.id, a.account_no, p.sum, p.recipient_account_no, p.create_time, p.payment_status_id \n" +
                "from payment as p \n" +
                "left join account as a on p.payer_account_id = a.id\n" +
                "left join user_has_account as uha on a.id = uha.account_id\n" +
                "where uha.user_id = ?";//**********
        public static final String PAYMENT_GET_BY_ID = "select p.id, a.id, a.account_no, p.sum, p.recipient_account_no, p.create_time, p.payment_status_id \n" +
                "from payment as p \n" +
                "left join account as a on p.payer_account_id = a.id\n" +
                "left join user_has_account as uha on a.id = uha.account_id\n" +
                "where p.id = ?";//**********
        public static final String GET_AMOUNT_BY_ACCOUNT_NUMBER = "select amount from account where account_number = ?";
        public static final String UPDATE_ACCOUNT_BALANCE_BY_ACCOUNT_ID = "update account set balance = ? where id = ? ";
        public static final String UPDATE_ACCOUNT_BLOCKED_BY_ACCOUNT_ID = "update account set is_blocked = ? where id = ? ";
        public static final String UPDATE_PAYMENT_STATUS_BY_PAYMENT_ID ="update payment set payment_status_id = ? where id = ? ";
        public static final String ACCOUNT_FIND_BY_ID = "select * from account where id = ?";
        public static final String ACCOUNT_GET_ALL = "select * from account";
        public static final String REQUEST_ACCOUNT_GET_ALL = "select * from request_account";
        public static final String ACCOUNT_FIND_BY_ACCOUNT_NO = "select * from account where account_no = ?";
        public static final String ACCOUNT_FIND_ID_BY_NUMBER = "select id from account where account_no = ?";
        public static final String PAYMENT_GET_ALL_BY_STATUS = "select * from payment where payment_status like ?";
        public static final String PAYMENT_GET_BY_PAYMENT_NUMBER = "select * from payment where payment_number = ?";
        public static final String ACCOUNT_UPDATE = "update account set user_login = ?, amount = ?, currency_name = ?, status = ? where account_number = ?";
        public static final String UPDATE_PAYMENT = "update payment set payment_from_account_num = ?,  payment_to_account_num = ?, time = ?, amount = ?, payment_status = ?, sender = ?, recipient = ? where payment_number = ?";


}
