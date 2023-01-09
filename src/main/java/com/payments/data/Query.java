package com.payments.data;

public class Query {
        public static final String USER_CREATE = "insert into user values(default,?,?,?,?,?,?)";
        public static final String REQUEST_DELETE = "delete from request_account where account_id = ?";
        public static final String USER_GET_BY_EMAIL = "select * from user where email= ?";
        public static final String USER_GET_ALL = "select * from user";
        public static final String CARD_GET_BY_ACCOUNT_ID = "select * from credit_card where account_id = ?";
        public static final String CARD_INSERT = "insert into credit_card values(default,?,?,?,?,?)";
        public static final String PAYMENT_CREATE = "insert into payment values(default,?,?,?,?,?,default)";
        public static final String ACCOUNT_UNBLOCK_REQUEST_INSERT = "insert into request_account values(default,?)";
        public static final String ACCOUNT_GET_ALL_BY_USER_ID = "select * from account as acc left join user_has_account as uha on acc.id= uha.account_id where uha.user_id = ?";
        public static final String USER_HAS_ACCOUNT_INSERT = "insert into user_has_account values (?,?)";
        public static final String ACCOUNT_INSERT = "insert into account values (default, ?,?,?,?,?,?,?, default)";
        public static final String PAYMENT_GET_BY_USER_ID = "select p.id, a.id, a.account_no, p.sum, p.recipient_account_no, p.create_time, p.payment_status_id " +
                "from payment as p " +
                "left join account as a on p.payer_account_id = a.id " +
                "left join user_has_account as uha on a.id = uha.account_id " +
                "where uha.user_id = ?";//**********
        public static final String PAYMENT_GET_BY_ID = "select p.id, a.id, a.account_no, p.sum, p.recipient_account_no, p.create_time, p.payment_status_id " +
                "from payment as p " +
                "left join account as a on p.payer_account_id = a.id " +
                "left join user_has_account as uha on a.id = uha.account_id " +
                "where p.id = ?";//**********
        public static final String UPDATE_ACCOUNT_BALANCE_BY_ACCOUNT_ID = "update account set balance = ? where id = ? ";
        public static final String UPDATE_ACCOUNT_BLOCKED_BY_ACCOUNT_ID = "update account set is_blocked = ? where id = ? ";

        public static final String UPDATE_USER_BLOCKED_BY_ACCOUNT_ID = "update user set is_blocked = ? where id = ? ";
        public static final String UPDATE_PAYMENT_STATUS_BY_PAYMENT_ID ="update payment set payment_status_id = ? where id = ? ";
        public static final String ACCOUNT_FIND_BY_ID = "select * from account where id = ?";
        public static final String USER_FIND_BY_ID = "select * from user where id = ?";
        public static final String ACCOUNT_GET_ALL = "select * from account";
        public static final String REQUEST_ACCOUNT_GET_ALL = "select * from request_account";
        public static final String REQUEST_ACCOUNT_GET_BY_ID = "select * from request_account where id = ?";

        public static final String ACCOUNT_FIND_BY_ACCOUNT_NO = "select * from account where account_no = ?";
        public static final String ACCOUNT_FIND_ID_BY_NUMBER = "select id from account where account_no = ?";
        public static final String ACCOUNT_UPDATE = "UPDATE account " +
                "SET " +
                "account_no = ?," +
                "currency = ?," +
                "balance = ?," +
                "owner_name = ?," +
                "owner_phone = ?," +
                "owner_adress = ?," +
                "postal_code = ?," +
                "is_blocked = ?" +
                "WHERE id = ?;";
        public static final String ACCOUNT_DELETE = "delete from account where id = ?";
        public static final String CARD_GET_BY_ID = "select * from credit_card where id = ?";
        public static final String CARD_GET_ALL = "select * from credit_card";
        public static final String CARD_DELETE = "delete from card where id = ?";
        public static final String CARD_UPDATE = "UPDATE credit_card " +
                "SET " +
                "account_id = ?," +
                "card_no = ?," +
                "cvc = ?," +
                "exp_month = ?," +
                "exp_year = ?" +
                "WHERE id = ?;";
        public static final String PAYMENT_GET_ALL = "select p.id, a.id, a.account_no, p.sum, p.recipient_account_no, p.create_time, p.payment_status_id " +
                "from payment as p " +
                "left join account as a on p.payer_account_id = a.id " +
                "left join user_has_account as uha on a.id = uha.account_id;";//**********;
        public static final String PAYMENT_UPDATE = "UPDATE payment " +
                "SET" +
                "sum = ?," +
                "payer_account_id = ?," +
                "recipient_account_no = ?," +
                "recipient_name = ?," +
                "payment_status_id = ? " +
                "WHERE id = ?;";
        public static final String PAYMENT_DELETE = "delete from payment where id = ?;";
        public static final String USER_UPDATE = "UPDATE user " +
                "SET " +
                "email = ?," +
                "password = ?," +
                "name = ?," +
                "surname = ?," +
                "role_id = ?," +
                "is_blocked = ? " +
                "WHERE id = ?;";
        public static final String USER_DELETE = "delete from user where id = ?;";
        public static final String ACCOUNT_UNBLOCK_REQUEST_UPDATE = "UPDATE request_account " +
                "SET " +
                "account_id = ? " +
                "WHERE id = ?;";
        public static final String USER_WITH_ACCOUNT_COUNT = "select " +
                "count(user_id) as users_count " +
                "FROM user_has_account " +
                "where account_id = ?";
        public static final String USER_HAS_ACCOUNT_DELETE = "delete from user_has_account where user_id=? AND account_id=?";
        public static final String CARD_DELETE_BY_ACCOUNT_ID = "delete from credit_card where account_id = ?";
}
