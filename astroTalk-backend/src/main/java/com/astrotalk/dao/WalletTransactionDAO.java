package com.astrotalk.dao;

import com.astrotalk.entity.User;
import com.astrotalk.entity.WalletTransaction;
import com.astrotalk.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Data access layer wrapping {@link WalletTransactionRepository} for {@link WalletTransaction} entity
 * operations. Provides methods for saving transactions and querying user transaction history.
 */
@Component
@RequiredArgsConstructor
public class WalletTransactionDAO {

    private static final Logger log = LoggerFactory.getLogger(WalletTransactionDAO.class);
    private final WalletTransactionRepository walletTransactionRepository;

    /**
     * Persists a new wallet transaction.
     *
     * @param walletTransaction the transaction entity to save
     * @return the saved transaction entity
     */
    public WalletTransaction save(WalletTransaction walletTransaction) {
        log.debug("Saving wallet transaction for user: {}", walletTransaction.getUser().getId());
        try {
            return walletTransactionRepository.save(walletTransaction);
        } catch (Exception e) {
            log.error("Error saving wallet transaction for user: {}", walletTransaction.getUser().getId(), e);
            throw e;
        }
    }

    /**
     * Finds all transactions for a user ordered by creation date descending.
     *
     * @param user     the user entity
     * @param pageable the pagination information
     * @return a page of wallet transactions
     */
    public Page<WalletTransaction> findByUserOrderByCreatedAtDesc(User user, Pageable pageable) {
        log.debug("Finding wallet transactions by user: {}", user.getId());
        try {
            return walletTransactionRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        } catch (Exception e) {
            log.error("Error finding wallet transactions by user: {}", user.getId(), e);
            throw e;
        }
    }
}
