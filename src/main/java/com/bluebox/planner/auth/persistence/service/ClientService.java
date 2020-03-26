package com.bluebox.planner.auth.persistence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
public class ClientService implements ClientDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//        String sql = "select c.* from  tbl_client c where c.client_id=?";
//        PreparedStatementSetter preparedStatementSetter = preparedStatement -> preparedStatement.setString(1, clientId);
//        ResultSetExtractor<ClientDetails> tRowMapper = resultSet -> {
//            resultSet.next();
//            Client client = new Client();
//            client.setClientId(resultSet.getString("client_id"));
//            client.setClientSecret(resultSet.getString("client_secret"));
//            client.setGrantType(resultSet.getString("grant_type"));
//            client.setAccessTokenValiditySeconds(resultSet.getInt("access_token_validity_seconds"));
//            client.setRefreshTokenValiditySeconds(resultSet.getInt("refresh_token_validity_seconds"));
//            return client;
//        };
//        return jdbcTemplate.query(sql, preparedStatementSetter, tRowMapper);
        return  null;
    }
}
