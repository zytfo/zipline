package com.zipline.repository;

import com.zipline.model.NFT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * The NFT repository contains methods for NFT entities.
 */
@Repository
public interface NFTRepository extends JpaRepository<NFT, BigInteger> {
}
