package main.java.org.compile;

public class Token {
    public enum TOKEN_TYPE {
        TK_INT_LITERAL,                  // [0-9]+
        TK_FLOAT_LITERAL,                // [0-9]+.[0-9]+
        TK_BOOL_LITERAL,                 // true or false
        TK_CHAR_LITERAL,                 // 'a'
        TK_IDENTIFIER,                   // identifier

        // keywords
        TK_INT,                          // int
        TK_FLOAT,                        // float
        TK_BOOL,                         // bool
        TK_VOID,                         // void
        TK_CHAR,                         // char
        TK_RETURN,                       // return
        TK_IF,                           // if
        TK_ELSE,                         // else
        TK_WHILE,                        // while
        TK_FOR,                          // for
        TK_BREAK,                        // break
        TK_CONTINUE,                     // continue
        TK_PRINT,                        // print

        TK_PLUS,                         // "+"
        TK_MINUS,                        // "-"
        TK_MULorDEREF,                   // "*"
        TK_DIV,                          // "/"
        TK_ADDRESS,                      // "&"
        TK_NOT,                          // "!"
        TK_LPAREN,                       // "("
        TK_RPAREN,                       // ")"
        TK_LBRACE,                       // "{"
        TK_RBRACE,                       // "}"
        TK_LBRACK,                       // "["
        TK_RBRACK,                       // "]"
        TK_ASSIGN,                       // "="
        TK_COMMA,                        // ","
        TK_SEMICOLON,                    // ";"

        // comparison operators
        TK_AND,                     // "&&"
        TK_OR,                      // "||"
        TK_EQ,                      // "=="
        TK_NE,                      // "!="
        TK_LT,                      // "<"
        TK_LE,                      // "<="
        TK_GT,                      // ">"
        TK_GE,                      // ">="

        TK_EOF,
    }
    public TOKEN_TYPE type;
    public String value;
    public int lineNum;
    public int column;
    public int width;

    public Token() {
    }
    public Token(TOKEN_TYPE type, String value) {
        this.type = type;
        this.value = value;
    }

    public Token(TOKEN_TYPE type, String value, int lineNum, int column) {
        this.type = type;
        this.value = value;
        this.lineNum = lineNum;
        this.column = column;
    }
    public Token(TOKEN_TYPE type, String value, int lineNum, int column, int width) {
        this.type = type;
        this.value = value;
        this.lineNum = lineNum;
        this.column = column;
        this.width = width;
    }
}
