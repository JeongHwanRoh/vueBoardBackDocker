package com.vueboard.global.utils;

import java.security.SecureRandom;

/**
 * Lightweight ULID-like generator (Crockford base32, 26 chars).
 * 
 * - No external dependencies
 * - Lexicographically sortable by time
 */
public final class UlidUtil {

	private static final char[] CROCKFORD_BASE32 = "0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray();
	private static final int TIME_BYTES = 6; // 48-bit millis
	private static final int RANDOM_BYTES = 10; // 80-bit randomness

	private static final SecureRandom RANDOM = new SecureRandom();

	private UlidUtil() {
	}

	public static String newUlid() {
		byte[] data = new byte[TIME_BYTES + RANDOM_BYTES];

		long time = System.currentTimeMillis();
		// 48-bit time, big-endian
		data[0] = (byte) (time >>> 40);
		data[1] = (byte) (time >>> 32);
		data[2] = (byte) (time >>> 24);
		data[3] = (byte) (time >>> 16);
		data[4] = (byte) (time >>> 8);
		data[5] = (byte) (time);

		byte[] random = new byte[RANDOM_BYTES];
		RANDOM.nextBytes(random);
		System.arraycopy(random, 0, data, TIME_BYTES, RANDOM_BYTES);

		return encodeCrockfordBase32(data);
	}

	private static String encodeCrockfordBase32(byte[] bytes) {
		// ULID is 128 bits -> 26 chars base32 (130 bits; top 2 bits are zero)
		StringBuilder sb = new StringBuilder(26);
		int buffer = 0;
		int bitsLeft = 0;

		for (byte b : bytes) {
			buffer = (buffer << 8) | (b & 0xFF);
			bitsLeft += 8;
			while (bitsLeft >= 5) {
				int index = (buffer >>> (bitsLeft - 5)) & 0x1F;
				bitsLeft -= 5;
				sb.append(CROCKFORD_BASE32[index]);
			}
		}

		if (bitsLeft > 0) {
			int index = (buffer << (5 - bitsLeft)) & 0x1F;
			sb.append(CROCKFORD_BASE32[index]);
		}

		// Ensure fixed length 26
		while (sb.length() < 26) {
			sb.append('0');
		}
		if (sb.length() > 26) {
			return sb.substring(0, 26);
		}
		return sb.toString();
	}
}
