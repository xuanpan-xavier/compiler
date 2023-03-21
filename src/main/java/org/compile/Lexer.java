package main.java.org.compile;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public String inputString;
    public List<Token> tokenList;
    private int pos = 0;
    private int lineNum = 1;
    private int column = 0;

    public Lexer(String text) {
        this.inputString = text;
    }

    public  Token getNextToken() {
        char currentChar = getChar();
        // ignore blank
        while (Character.isWhitespace(currentChar)) {
            if (currentChar == '\n') {
                lineNum++;
                column = 1;
            }
            currentChar = getChar();
        }
        // ignore comment
        while (currentChar == '/') {
            if ((currentChar = getChar()) == '/') { // line comment
                while ((currentChar = getChar()) != '\n' && inputString.length() >= pos) {
                    continue;
                }
            } else if (currentChar == '*') { // block comment
                if ((currentChar = getChar()) == '/') {
                    printErr(lineNum, column-2, "missing '*' before '/'");
                    System.exit(1);
                }
                while (currentChar != '/') {
                    if (currentChar == '\n') {
                        lineNum++;
                        column = 1;
                    }
                    currentChar = getChar();
                }
                if (currentChar == '/') {
                    putBackChar();
                    putBackChar();
                    if ((currentChar = getChar()) != '*') {
                        printErr(lineNum, column-2, "missing '*' before '/'");
                        System.exit(1);
                    } else {
                        getChar();
                        currentChar = getChar();
                    }
                }
            } else {
                printErr(lineNum, column-3, "missing '*' or '/'");
                System.exit(1);
            }

            // ignore blank
            while (Character.isWhitespace(currentChar)) {
                if (currentChar == '\n') {
                    lineNum++;
                    column = 1;
                }
                currentChar = getChar();
            }
        }

        // Integer or Float
        if (Character.isDigit(currentChar) || currentChar == '.') {
            String numberString = "";

            if (currentChar == '.') { // Float
                do {
                    numberString += currentChar;
                    currentChar = getChar();
                } while (Character.isDigit(currentChar));
                putBackChar();
                return  new Token(Token.TOKEN_TYPE.TK_FLOAT_LITERAL, numberString, lineNum, column);
            } else { // maybe Integer
                do {
                    numberString += currentChar;
                    currentChar = getChar();
                } while (Character.isDigit(currentChar));

                if (currentChar == '.') { // Float
                    do {
                        numberString += currentChar;
                        currentChar = getChar();
                    } while (Character.isDigit(currentChar));
                    putBackChar();
                    return  new Token(Token.TOKEN_TYPE.TK_FLOAT_LITERAL, numberString, lineNum, column);
                } else { //Integer
                    putBackChar();
                    return new Token(Token.TOKEN_TYPE.TK_INT_LITERAL, numberString, lineNum, column);
                }
            }
        }

        // Keywords or Identifier
        if (Character.isLetter(currentChar) || currentChar == '_') {
            String IdentifierString = "";
            do {
                IdentifierString += currentChar;
                currentChar = getChar();
            } while (Character.isLetterOrDigit(currentChar) || currentChar == '_');
            putBackChar();

            // Keywords
            if (IdentifierString.equals("int") )
                return new Token(Token.TOKEN_TYPE.TK_INT, IdentifierString, lineNum, column);
            if (IdentifierString.equals("float"))
                return new Token(Token.TOKEN_TYPE.TK_FLOAT, IdentifierString, lineNum, column);
            if (IdentifierString.equals("bool"))
                return new Token(Token.TOKEN_TYPE.TK_BOOL, IdentifierString, lineNum, column);
            if (IdentifierString.equals("void"))
                return new Token(Token.TOKEN_TYPE.TK_VOID, IdentifierString, lineNum, column);
            if (IdentifierString.equals("char"))
                return new Token(Token.TOKEN_TYPE.TK_CHAR, IdentifierString, lineNum, column);
            if (IdentifierString.equals("true") || IdentifierString.equals("false"))
                return new Token(Token.TOKEN_TYPE.TK_BOOL_LITERAL, IdentifierString, lineNum, column);
            if (IdentifierString.equals("return"))
                return new Token(Token.TOKEN_TYPE.TK_RETURN, IdentifierString, lineNum, column);
            if (IdentifierString.equals("if"))
                return new Token(Token.TOKEN_TYPE.TK_IF, IdentifierString, lineNum, column);
            if (IdentifierString.equals("else"))
                return new Token(Token.TOKEN_TYPE.TK_ELSE, IdentifierString, lineNum, column);
            if (IdentifierString.equals("while"))
                return new Token(Token.TOKEN_TYPE.TK_WHILE, IdentifierString, lineNum, column);
            if (IdentifierString.equals("for"))
                return new Token(Token.TOKEN_TYPE.TK_FOR, IdentifierString, lineNum, column);
            if (IdentifierString.equals("break"))
                return new Token(Token.TOKEN_TYPE.TK_BREAK, IdentifierString, lineNum, column);
            if (IdentifierString.equals("continue"))
                return new Token(Token.TOKEN_TYPE.TK_CONTINUE, IdentifierString, lineNum, column);
            if (IdentifierString.equals("print"))
                return new Token(Token.TOKEN_TYPE.TK_PRINT, IdentifierString, lineNum, column);

            // Identifier
            return new Token(Token.TOKEN_TYPE.TK_IDENTIFIER, IdentifierString, lineNum, column);
        }

        // Operator or Separator
        if (currentChar == '+') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_PLUS, s, lineNum, column);
        }
        if (currentChar == '-') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_MINUS, s, lineNum, column);
        }
        if (currentChar == '*') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_MULorDEREF, s, lineNum, column);
        }
        if (currentChar == '/') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_DIV, s, lineNum, column);
        }
        if (currentChar == '(') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_LPAREN, s, lineNum, column);
        }
        if (currentChar == ')') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_RPAREN, s, lineNum, column);
        }
        if (currentChar == '{') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_LBRACE, s, lineNum, column);
        }
        if (currentChar == '}') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_RBRACE, s, lineNum, column);
        }
        if (currentChar == '[') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_LBRACK, s, lineNum, column);
        }
        if (currentChar == ']') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_RBRACK, s, lineNum, column);
        }
        if (currentChar == '&') {
            String s = String.valueOf(currentChar);
            currentChar = getChar();
            if (currentChar == '&') {
                s += currentChar;
                return new Token(Token.TOKEN_TYPE.TK_AND, s, lineNum, column);
            } else putBackChar();
            return new Token(Token.TOKEN_TYPE.TK_ADDRESS, s, lineNum, column);
        }
        if (currentChar == '|') {
            String s = String.valueOf(currentChar);
            currentChar = getChar();
            if (currentChar == '|') {
                s += currentChar;
                return new Token(Token.TOKEN_TYPE.TK_OR, s, lineNum, column);
            } else putBackChar();
        }
        if (currentChar == '=') {
            String s = String.valueOf(currentChar);
            currentChar = getChar();
            if (currentChar == '=') {
                s += currentChar;
                return new Token(Token.TOKEN_TYPE.TK_EQ, s, lineNum, column);
            } else putBackChar();
            return new Token(Token.TOKEN_TYPE.TK_ASSIGN, s, lineNum, column);
        }
        if (currentChar == '!') {
            String s = String.valueOf(currentChar);
            currentChar = getChar();
            if (currentChar == '=') {
                s += currentChar;
                return new Token(Token.TOKEN_TYPE.TK_NE, s, lineNum, column);
            } else putBackChar();
            return new Token(Token.TOKEN_TYPE.TK_NOT, s, lineNum, column);
        }
        if (currentChar == '<') {
            String s = String.valueOf(currentChar);
            currentChar = getChar();
            if (currentChar == '=') {
                s += currentChar;
                return new Token(Token.TOKEN_TYPE.TK_LE, s, lineNum, column);
            } else putBackChar();
            return new Token(Token.TOKEN_TYPE.TK_LT, s, lineNum, column);
        }
        if (currentChar == '>') {
            String s = String.valueOf(currentChar);
            currentChar = getChar();
            if (currentChar == '=') {
                s += currentChar;
                return new Token(Token.TOKEN_TYPE.TK_GE, s, lineNum, column);
            } else putBackChar();
            return new Token(Token.TOKEN_TYPE.TK_GT, s, lineNum, column);
        }
        if (currentChar == ';') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_SEMICOLON, s, lineNum, column);
        }
        if (currentChar == ',') {
            String s = String.valueOf(currentChar);
            return new Token(Token.TOKEN_TYPE.TK_COMMA, s, lineNum, column);
        }
//        if (currentChar == '\'') {
//            if ((currentChar = getChar()) == '\0')
//                printErr(lineNum,column, "unclosed char literal");
//            //char c;
//            if (currentChar == '\\') {
//                currentChar = getChar();
//                // java have not something like \a,\b
//                // c = currentChar;
//            }
//            if ((currentChar = getChar()) != '\'')
//                printErr(lineNum,column, "unclosed char literal");
//            String s = String.valueOf(currentChar);
//            return new Token(Token.TOKEN_TYPE.TK_CHAR_LITERAL, s, lineNum, column);
//        }
        if (inputString.length() <= pos) {
            return new Token(Token.TOKEN_TYPE.TK_EOF, "EOF");
        }
        printErr("%c is an invalid token\n", currentChar);
        System.exit(1);
        return new Token();
    }

    public List<Token> getAllToken() {
        this.tokenList = new ArrayList<>();
        Token currentToken;
        do {
            currentToken = getNextToken();
            tokenList.add(currentToken);
        } while (currentToken.type != Token.TOKEN_TYPE.TK_EOF);
        return tokenList;
    }

    private char getChar() {
        column++;
        if (inputString.length() <= pos) {
            pos++;
            return '`';
        }
        return inputString.charAt(pos++);
    }
    private void putBackChar() {
        pos--;
        column--;
    }

    public void printErr(int lineNum, int column, String message) {
        System.out.printf("error at %d:%d\n", lineNum+1, column+1);
        System.out.printf(message);
    }
    public void printErr(String message, char msg) {
        System.out.printf(message, msg);
    }

    public static void main(String[] args) {
        Lexer lexer = new Lexer("int main() { if (true) return 0;}");
        List<Token> tokens = lexer.getAllToken();
        for (Token token : tokens) {
            System.out.println(token.type + " : " + token.value);
        }

    }

}


