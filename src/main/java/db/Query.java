package db;

public class Query {
        public static final String USER_EXIST_BY_LOGIN = "select * from user_entity where email = ?";
        public static final String USER_CREATE = "insert into user values(default,?,?,?,?,?,?)";
        public static final String USER_UPDATE = "update user_entity set name = ?, surname = ?, email = ? , password = ?, role =?, status = ?  where id = ?";
        public static final String USER_DELETE = "delete from user_entity where  id = ?";
        public static final String USER_GET_BY_ID = "select * from user_entity where id= ?";
        public static final String USER_GET_BY_EMAIL = "select * from user where email= ?";
        public static final String USER_GET_ALL = "select * from user_entity ";
        public static final String ROLE_GET_BY_NAME = "select * from user_role where name = ?";
        public static final String ROLE_GET_BY_ID = "select * from user_role where id = ?";
        public static final String ROLE_GET_ALL = "select * from user_role";
        public static final String CARD_GET_BY_ID = "select * from card where id = ?";
        public static final String CARD_GET_ALL = "select * from card";
        public static final String CARD_GET_BY_CARD_NUMBER = "select * from card where card_number = ?";
        public static final String CARD_CREATE = "insert into card values(default,?,?,?,?,?,?)";
        public static final String CARD_DELETE = "delete from card where id = ?";
        public static final String CARD_UPDATE = "update card set card_number = ?, pin_num = ?, cvv_num = ?, expiry_date = ?, card_type = ? where id = ?";
        public static final String PAYMENT_CREATE = "insert into payment values(default,?,?,?,?,?,?,?,?)";

        public static final String PAYMENT_GET_BY_ACCOUNT_FROM_ID = "select * from payment where payment_from_account_id = ?";
        public static final String PAYMENT_GET_BY_ACCOUNT_TO_ID = "select * from payment where payment_to_account_id = ?";

        public static final String ACCOUNT_GET_ALL_BY_USER_ID = "select * from account where card_holder_id = ?";
        public static final String ACCOUNT_CREATE = "insert into account values (default, ?,?,?,?,?)";
        public static final String VAULT = "insert into vault values (default) returning number";
        public static final String ACCOUNT_GET_ALL_BY_USER_EMAIL = "select * from account where user_login like ? ";
        public static final String CARD_GET_ALL_BY_ACCOUNT_NUMBER = "select * from card where account_num = ?";
        public static final String PAYMENT_GET_BY_USER_EMAIL = "select * from payment where sender = ?";
        public static final String GET_AMOUNT_BY_ACCOUNT_NUMBER = "select amount from account where account_number = ?";
        public static final String UPDATE_ACCOUNT_AMOUNT_BY_ACCOUNT_NUMBER = "update account set amount = ? where account_number = ? ";
        public static final String ACCOUNT_FIND_BY_NUMBER = "select * from account where account_number = ?";
        public static final String PAYMENT_GET_ALL_BY_STATUS = "select * from payment where payment_status like ?";
        public static final String PAYMENT_GET_BY_PAYMENT_NUMBER = "select * from payment where payment_number = ?";
        public static final String ACCOUNT_UPDATE = "update account set user_login = ?, amount = ?, currency_name = ?, status = ? where account_number = ?";
        public static final String UPDATE_PAYMENT = "update payment set payment_from_account_num = ?,  payment_to_account_num = ?, time = ?, amount = ?, payment_status = ?, sender = ?, recipient = ? where payment_number = ?";


}
