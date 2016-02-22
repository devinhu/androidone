/*
 * Java Base64 - A pure Java library for reading and writing Base64
 *               encoded streams.
 * 
 * Copyright (C) 2007-2009 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.sd.core.utils.encrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Base64 encoding and decoding utility methods, both for binary and textual
 * informations.
 * </p>
 * 
 * @author Carlo Pelliccia
 * @since 1.1
 * @version 1.3
 */
public class Base64 {

	/**
	 * <p>
	 * Encodes a string.
	 * </p>
	 * <p>
	 * Before the string is encoded in Base64, it is converted in a binary
	 * sequence using the system default charset.
	 * </p>
	 * 
	 * @param str
	 *            The source string.
	 * @return The encoded string.
	 * @throws RuntimeException
	 *             If an unexpected error occurs.
	 */
	public static String encode(String str) throws RuntimeException {
		byte[] bytes = str.getBytes();
		byte[] encoded = encode(bytes);
		try {
			return new String(encoded, "ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
	}

	/**
	 * <p>
	 * Encodes a string.
	 * </p>
	 * <p>
	 * Before the string is encoded in Base64, it is converted in a binary
	 * sequence using the supplied charset.
	 * </p>
	 * 
	 * @param str
	 *            The source string
	 * @param charset
	 *            The charset name.
	 * @return The encoded string.
	 * @throws RuntimeException
	 *             If an unexpected error occurs.
	 * @since 1.2
	 */
	public static String encode(String str, String charset)
			throws RuntimeException {
		byte[] bytes;
		try {
			bytes = str.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported charset: " + charset, e);
		}
		byte[] encoded = encode(bytes);
		try {
			return new String(encoded, "ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
	}

	/**
	 * <p>
	 * Decodes the supplied string.
	 * </p>
	 * <p>
	 * The supplied string is decoded into a binary sequence, and then the
	 * sequence is encoded with the system default charset and returned.
	 * </p>
	 * 
	 * @param str
	 *            The encoded string.
	 * @return The decoded string.
	 * @throws RuntimeException
	 *             If an unexpected error occurs.
	 */
	public static String decode(String str) throws RuntimeException {
		byte[] bytes;
		try {
			bytes = str.getBytes("ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
		byte[] decoded = decode(bytes);
		return new String(decoded);
	}

	/**
	 * <p>
	 * Decodes the supplied string.
	 * </p>
	 * <p>
	 * The supplied string is decoded into a binary sequence, and then the
	 * sequence is encoded with the supplied charset and returned.
	 * </p>
	 * 
	 * @param str
	 *            The encoded string.
	 * @param charset
	 *            The charset name.
	 * @return The decoded string.
	 * @throws RuntimeException
	 *             If an unexpected error occurs.
	 * @since 1.2
	 */
	public static String decode(String str, String charset)
			throws RuntimeException {
		byte[] bytes;
		try {
			bytes = str.getBytes("ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
		byte[] decoded = decode(bytes);
		try {
			return new String(decoded, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported charset: " + charset, e);
		}
	}

	/**
	 * <p>
	 * Encodes a binary sequence.
	 * </p>
	 * <p>
	 * If data are large, i.e. if you are working with large binary files,
	 * consider to use a {@link Base64OutputStream} instead of loading too much
	 * data in memory.
	 * </p>
	 * 
	 * @param bytes
	 *            The source sequence.
	 * @return The encoded sequence.
	 * @throws RuntimeException
	 *             If an unexpected error occurs.
	 * @since 1.2
	 */
	public static byte[] encode(byte[] bytes) throws RuntimeException {
		return encode(bytes, 0);
	}

	/**
	 * <p>
	 * Encodes a binary sequence, wrapping every encoded line every
	 * <em>wrapAt</em> characters. A <em>wrapAt</em> value less than 1 disables
	 * wrapping.
	 * </p>
	 * <p>
	 * If data are large, i.e. if you are working with large binary files,
	 * consider to use a {@link Base64OutputStream} instead of loading too much
	 * data in memory.
	 * </p>
	 * 
	 * @param bytes
	 *            The source sequence.
	 * @param wrapAt
	 *            The max line length for encoded data. If less than 1 no wrap
	 *            is applied.
	 * @return The encoded sequence.
	 * @throws RuntimeException
	 *             If an unexpected error occurs.
	 * @since 1.2
	 */
	public static byte[] encode(byte[] bytes, int wrapAt)
			throws RuntimeException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			encode(inputStream, outputStream, wrapAt);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected I/O error", e);
		} finally {
			try {
				inputStream.close();
			} catch (Throwable t) {
				;
			}
			try {
				outputStream.close();
			} catch (Throwable t) {
				;
			}
		}
		return outputStream.toByteArray();
	}

	/**
	 * <p>
	 * Decodes a binary sequence.
	 * </p>
	 * <p>
	 * If data are large, i.e. if you are working with large binary files,
	 * consider to use a {@link Base64InputStream} instead of loading too much
	 * data in memory.
	 * </p>
	 * 
	 * @param bytes
	 *            The encoded sequence.
	 * @return The decoded sequence.
	 * @throws RuntimeException
	 *             If an unexpected error occurs.
	 * @since 1.2
	 */
	public static byte[] decode(byte[] bytes) throws RuntimeException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			decode(inputStream, outputStream);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected I/O error", e);
		} finally {
			try {
				inputStream.close();
			} catch (Throwable t) {
				;
			}
			try {
				outputStream.close();
			} catch (Throwable t) {
				;
			}
		}
		return outputStream.toByteArray();
	}

	/**
	 * <p>
	 * Encodes data from the given input stream and writes them in the given
	 * output stream.
	 * </p>
	 * <p>
	 * The supplied input stream is read until its end is reached, but it's not
	 * closed by this method.
	 * </p>
	 * <p>
	 * The supplied output stream is nor flushed neither closed by this method.
	 * </p>
	 * 
	 * @param inputStream
	 *            The input stream.
	 * @param outputStream
	 *            The output stream.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public static void encode(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		encode(inputStream, outputStream, 0);
	}

	/**
	 * <p>
	 * Encodes data from the given input stream and writes them in the given
	 * output stream, wrapping every encoded line every <em>wrapAt</em>
	 * characters. A <em>wrapAt</em> value less than 1 disables wrapping.
	 * </p>
	 * <p>
	 * The supplied input stream is read until its end is reached, but it's not
	 * closed by this method.
	 * </p>
	 * <p>
	 * The supplied output stream is nor flushed neither closed by this method.
	 * </p>
	 * 
	 * @param inputStream
	 *            The input stream from which clear data are read.
	 * @param outputStream
	 *            The output stream in which encoded data are written.
	 * @param wrapAt
	 *            The max line length for encoded data. If less than 1 no wrap
	 *            is applied.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public static void encode(InputStream inputStream,
			OutputStream outputStream, int wrapAt) throws IOException {
		Base64OutputStream aux = new Base64OutputStream(outputStream, wrapAt);
		copy(inputStream, aux);
		aux.commit();
	}

	/**
	 * <p>
	 * Decodes data from the given input stream and writes them in the given
	 * output stream.
	 * </p>
	 * <p>
	 * The supplied input stream is read until its end is reached, but it's not
	 * closed by this method.
	 * </p>
	 * <p>
	 * The supplied output stream is nor flushed neither closed by this method.
	 * </p>
	 * 
	 * @param inputStream
	 *            The input stream from which encoded data are read.
	 * @param outputStream
	 *            The output stream in which decoded data are written.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public static void decode(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		copy(new Base64InputStream(inputStream), outputStream);
	}

	/**
	 * <p>
	 * Encodes data from the given source file contents and writes them in the
	 * given target file, wrapping every encoded line every <em>wrapAt</em>
	 * characters. A <em>wrapAt</em> value less than 1 disables wrapping.
	 * </p>
	 * 
	 * @param source
	 *            The source file, from which decoded data are read.
	 * @param target
	 *            The target file, in which encoded data are written.
	 * @param wrapAt
	 *            The max line length for encoded data. If less than 1 no wrap
	 *            is applied.
	 * @throws IOException
	 *             If an I/O error occurs.
	 * @since 1.3
	 */
	public static void encode(File source, File target, int wrapAt)
			throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			Base64.encode(inputStream, outputStream, wrapAt);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Throwable t) {
					;
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Throwable t) {
					;
				}
			}
		}
	}

	/**
	 * <p>
	 * Encodes data from the given source file contents and writes them in the
	 * given target file.
	 * </p>
	 * 
	 * @param source
	 *            The source file, from which decoded data are read.
	 * @param target
	 *            The target file, in which encoded data are written.
	 * @throws IOException
	 *             If an I/O error occurs.
	 * @since 1.3
	 */
	public static void encode(File source, File target) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			Base64.encode(inputStream, outputStream);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Throwable t) {
					;
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Throwable t) {
					;
				}
			}
		}
	}

	/**
	 * <p>
	 * Decodes data from the given source file contents and writes them in the
	 * given target file.
	 * </p>
	 * 
	 * @param source
	 *            The source file, from which encoded data are read.
	 * @param target
	 *            The target file, in which decoded data are written.
	 * @throws IOException
	 *             If an I/O error occurs.
	 * @since 1.3
	 */
	public static void decode(File source, File target) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			decode(inputStream, outputStream);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Throwable t) {
					;
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Throwable t) {
					;
				}
			}
		}
	}

	/**
	 * Copies data from a stream to another.
	 * 
	 * @param inputStream
	 *            The input stream.
	 * @param outputStream
	 *            The output stream.
	 * @throws IOException
	 *             If a unexpected I/O error occurs.
	 */
	private static void copy(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		// 1KB buffer
		byte[] b = new byte[1024];
		int len;
		while ((len = inputStream.read(b)) != -1) {
			outputStream.write(b, 0, len);
		}
	}

}
