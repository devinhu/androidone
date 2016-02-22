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

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * A base64 encoding input stream.
 * </p>
 * 
 * <p>
 * A <em>Base64InputStream</em> reads from an underlying stream which is
 * supposed to be a base64 encoded stream. <em>Base64InputStream</em> decodes
 * the data read from the underlying stream and returns the decoded bytes to the
 * caller.
 * </p>
 * 
 * @author Carlo Pelliccia
 */
public class Base64InputStream extends InputStream {

	/**
	 * The underlying stream.
	 */
	private InputStream inputStream;

	/**
	 * The buffer.
	 */
	private int[] buffer;

	/**
	 * A counter for values in the buffer.
	 */
	private int bufferCounter = 0;

	/**
	 * End-of-stream flag.
	 */
	private boolean eof = false;

	/**
	 * <p>
	 * It builds a base64 decoding input stream.
	 * </p>
	 * 
	 * @param inputStream
	 *            The underlying stream, from which the encoded data is read.
	 */
	public Base64InputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public int read() throws IOException {
		if (buffer == null || bufferCounter == buffer.length) {
			if (eof) {
				return -1;
			}
			acquire();
			if (buffer.length == 0) {
				buffer = null;
				return -1;
			}
			bufferCounter = 0;
		}
		return buffer[bufferCounter++];
	}

	/**
	 * Reads from the underlying stream, decodes the data and puts the decoded
	 * bytes into the buffer.
	 */
	private void acquire() throws IOException {
		char[] four = new char[4];
		int i = 0;
		do {
			int b = inputStream.read();
			if (b == -1) {
				if (i != 0) {
					throw new IOException("Bad base64 stream");
				} else {
					buffer = new int[0];
					eof = true;
					return;
				}
			}
			char c = (char) b;
			if (Shared.chars.indexOf(c) != -1 || c == Shared.pad) {
				four[i++] = c;
			} else if (c != '\r' && c != '\n') {
				throw new IOException("Bad base64 stream");
			}
		} while (i < 4);
		boolean padded = false;
		for (i = 0; i < 4; i++) {
			if (four[i] != Shared.pad) {
				if (padded) {
					throw new IOException("Bad base64 stream");
				}
			} else {
				if (!padded) {
					padded = true;
				}
			}
		}
		int l;
		if (four[3] == Shared.pad) {
			if (inputStream.read() != -1) {
				throw new IOException("Bad base64 stream");
			}
			eof = true;
			if (four[2] == Shared.pad) {
				l = 1;
			} else {
				l = 2;
			}
		} else {
			l = 3;
		}
		int aux = 0;
		for (i = 0; i < 4; i++) {
			if (four[i] != Shared.pad) {
				aux = aux | (Shared.chars.indexOf(four[i]) << (6 * (3 - i)));
			}
		}
		buffer = new int[l];
		for (i = 0; i < l; i++) {
			buffer[i] = (aux >>> (8 * (2 - i))) & 0xFF;
		}
	}

	public void close() throws IOException {
		inputStream.close();
	}
}