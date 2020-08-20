
// File:   MH_Lexer.java

// Java template file for lexer component of Informatics 2A Assignment 1.
// Concerns lexical classes and lexer for the language MH (`Micro-Haskell').


import java.io.* ;

class MH_Lexer extends GenLexer implements LEX_TOKEN_STREAM {

static class VarAcceptor extends Acceptor implements DFA {
  public String lexClass() {return "VAR" ;} ;
  public int numberOfStates() {return 3 ;} ;

  int next (int state, char c) {
switch (state) {
case 0: if (CharTypes.isSmall(c)) return 1 ; else return 2 ;
case 1: if (CharTypes.isSmall(c) || CharTypes.isLarge(c) || CharTypes.isDigit(c) || c == '\'' ) return 1 ; else return 2 ;
default: return 2 ;
}
  }
  boolean accepting (int state) {return (state == 1) ;}
  int dead () {return 2 ;}
}

static class NumAcceptor extends Acceptor implements DFA {
  public String lexClass() {return "NUM" ;} ;
  public int numberOfStates() {return 4 ;} ;

  int next (int state, char c) {
switch (state) {
case 0: if (c=='0') return 1 ; else if (CharTypes.isDigit(c)) return 2; else return 3 ;
case 2: if (CharTypes.isDigit(c)) return 2 ; else return 3 ;

default: return 3 ;
}
  }
  boolean accepting (int state) {return (state == 1 || state == 2) ;}
  int dead () {return 3 ;}
}

static class BooleanAcceptor extends Acceptor implements DFA {
  public String lexClass() {return "BOOLEAN" ;} ;
  public int numberOfStates() {return 9 ;} ;

  int next (int state, char c) {
switch (state) {
case 0: if (c=='T') return 1 ; else if (c == 'F') return 5; else return 8;
case 1: if (c=='R') return 2 ; else return 8 ;
case 2: if (c=='U') return 3 ; else return 8 ;
case 3: if (c=='E') return 4 ; else return 8 ;
case 5: if (c=='A') return 6 ; else return 8 ;
case 6: if (c=='L') return 7 ; else return 8 ;
case 7: if (c=='S') return 3 ; else return 8 ;

default: return 8 ;
}
  }
  boolean accepting (int state) {return (state == 4) ;}
  int dead () {return 8 ;}
}

static class SymAcceptor extends Acceptor implements DFA {
  public String lexClass() {return "SYM" ;} ;
  public int numberOfStates() {return 3 ;} ;

  int next (int state, char c) {
switch (state) {
case 0: if (CharTypes.isSymbolic(c)) return 1 ; else return 2 ;
case 1: if (CharTypes.isSymbolic(c)) return 1; else return 2 ;
default: return 2 ;
}
  }
  boolean accepting (int state) {return (state == 1) ;}
  int dead () {return 2 ;}
}

static class WhitespaceAcceptor extends Acceptor implements DFA {
  public String lexClass() {return "" ;} ;
  public int numberOfStates() {return 3 ;} ;

  int next (int state, char c) {
switch (state) {
case 0: if (CharTypes.isWhitespace(c)) return 1 ; else return 2 ;
case 1: if (CharTypes.isWhitespace(c)) return 1; else return 2 ;
default: return 2 ;
}
  }
  boolean accepting (int state) {return (state == 1) ;}
  int dead () {return 2 ;}
}

static class CommentAcceptor extends Acceptor implements DFA {
  public String lexClass() {return "" ;} ;
  public int numberOfStates() {return 6 ;} ;

  int next (int state, char c) {
switch (state) {
case 0: if (c=='-') return 1 ; else return 4 ;
case 1: if (c=='-') return 2 ; else return 4 ;
case 2: if (c=='-') return 2 ; else if (!CharTypes.isNewline(c) && !CharTypes.isSymbolic(c)) return 3; else return 4;
case 3: if (!CharTypes.isNewline(c)) return 3; else return 4;
default: return 4;
}
  }
  boolean accepting (int state) {return (state == 2 || state == 3) ;}
  int dead () {return 4 ;}
}


static class TokAcceptor extends Acceptor implements DFA {

    String tok ;
    int tokLen ;
    TokAcceptor (String tok) {this.tok = tok ; tokLen = tok.length() ;}

    public String lexClass() {return tok;} ;
    public int numberOfStates() {return tokLen+2;} ;

    int next (int state, char c) {
      if (state < tokLen) {
        if (c == tok.charAt(state)) return state+1; else return tokLen+1;
      } return tokLen+1;

}
    boolean accepting (int state) {return (state == tokLen) ;}
    int dead () {return tokLen+1 ;}
    // add code here
}

    // add definitions of MH_acceptors here

    static DFA varAcc = new VarAcceptor() ;
    static DFA numAcc = new NumAcceptor() ;
    static DFA booleanAcc = new BooleanAcceptor() ;
    static DFA symAcc = new SymAcceptor() ;
    static DFA whitespaceAcc = new WhitespaceAcceptor() ;
    static DFA commentsAcc = new CommentAcceptor() ;
    static DFA thentokAcc = new TokAcceptor("then") ;
    static DFA iftokAcc = new TokAcceptor("if") ;
    static DFA elsetokAcc = new TokAcceptor("else") ;
    static DFA booltokAcc = new TokAcceptor("Bool") ;
    static DFA inttokAcc = new TokAcceptor("Integer") ;
    static DFA optokAcc = new TokAcceptor("(") ;
    static DFA cltokAcc = new TokAcceptor(")") ;
    static DFA sctokAcc = new TokAcceptor(";") ;

    static DFA[] MH_acceptors =
	new DFA[] {thentokAcc, iftokAcc, elsetokAcc, booltokAcc, inttokAcc, optokAcc, cltokAcc, sctokAcc, numAcc, booleanAcc, commentsAcc, symAcc,  whitespaceAcc, varAcc} ;

    MH_Lexer (Reader reader) {
	super(reader,MH_acceptors) ;
    }

}
