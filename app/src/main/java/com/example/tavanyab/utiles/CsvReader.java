package com.example.tavanyab.utiles;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by root on 5/8/16.
 */
public class CsvReader {
    // new String to stop internning
    static public String END_OF_LINE = new String("END_OF_LINE");

    private char[] field_delim = {','};
    private char block_delim = '\n';

    private Reader reader;
    private boolean newline;

    // should   bbb,,,ccc be considered to be two elements?
    // useful for log parsing.
    private boolean consume;

    public CsvReader(Reader rdr) {
        this.reader = rdr;
    }

    public void setFieldDelimiter(char[] ch) {
        field_delim = ch;
    }

    public void setBlockDelimiter(char ch) {
        block_delim = ch;
    }

    public void setConsuming(boolean b) {
        this.consume = b;
    }

    public boolean isConsuming() {
        return this.consume;
    }

    public String[] readLine() throws IOException {
        ArrayList list = new ArrayList();
        String str;

        while (true) {
            str = readField();
            if (str == null) {
                break;
            }
            if (str == END_OF_LINE) {
                break;
            }
            list.add(str);
        }

        if (list.isEmpty()) {
            return null;
        }

        return (String[]) list.toArray(new String[0]);
    }

    public String readField() throws IOException {
        if (this.newline) {
            this.newline = false;
            return END_OF_LINE;
        }

        StringBuffer buffer = new StringBuffer();
        boolean quoted = false;
        int last = -1;
        int ch = this.reader.read();

        if (ch == -1) {
            return null;
        }
        boolean endOfField = true;
        if (ch == '"') {
            quoted = true;
        } else if (ch == block_delim) {
            return END_OF_LINE;
        } else
            for (int i = 0; i < field_delim.length; i++) {
                if (ch == field_delim[i]) {
                    return "";
                } else {
                    endOfField = false;
                }
            }
        if (!endOfField) {
            buffer.append((char) ch);
        }
        while ((ch = this.reader.read()) != -1) {
            if (ch == block_delim) {
                this.newline = true;
                break;
            }
            if (quoted) {
                if (ch == '"') {
                    if (last == '"') {
                        // forget about this quote and move on
                        last = -1;
                        buffer.append('"');
                        continue;
                    }
                    last = '"';
                    continue;
                }
            }
            endOfField = false;
            for (int i = 0; i < field_delim.length; i++) {
                if (ch == field_delim[i]) {
                    if (quoted) {
                        if (last == '"') {
                            endOfField = true;
                            break;
                        }
                    } else {
                        endOfField = true;
                        break;
                    }
                }
            }
            if (endOfField) {
                break;
            }
            buffer.append((char) ch);
        }

        return buffer.toString();
    }

    public void close() throws IOException {
        this.reader.close();
    }

}
