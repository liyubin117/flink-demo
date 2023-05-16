/* Simple1.java */
/* Generated By:JavaCC: Do not edit this line. Simple1.java */
package codegen.javacc.simple1;

/** Simple brace matcher. */
public class Simple1 implements Simple1Constants {

    /** Main entry point. */
    public static void main(String args[]) throws ParseException {
        Simple1 parser = new Simple1(System.in);
        parser.Input();
    }

    /** Root production. */
    public static final void Input() throws ParseException {
        MatchedBraces();
        label_1:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 1:
                case 2:
                    {;
                        break;
                    }
                default:
                    jj_la1[0] = jj_gen;
                    break label_1;
            }
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 1:
                    {
                        jj_consume_token(1);
                        break;
                    }
                case 2:
                    {
                        jj_consume_token(2);
                        break;
                    }
                default:
                    jj_la1[1] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        jj_consume_token(0);
    }

    /** Brace matching production. */
    public static final void MatchedBraces() throws ParseException {
        jj_consume_token(3);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 3:
                {
                    MatchedBraces();
                    break;
                }
            default:
                jj_la1[2] = jj_gen;
                ;
        }
        jj_consume_token(4);
    }

    private static boolean jj_initialized_once = false;
    /** Generated Token Manager. */
    public static Simple1TokenManager token_source;

    static SimpleCharStream jj_input_stream;
    /** Current token. */
    public static Token token;
    /** Next token. */
    public static Token jj_nt;

    private static int jj_ntk;
    private static int jj_gen;
    private static final int[] jj_la1 = new int[3];
    private static int[] jj_la1_0;

    static {
        jj_la1_init_0();
    }

    private static void jj_la1_init_0() {
        jj_la1_0 =
                new int[] {
                    0x6, 0x6, 0x8,
                };
    }

    /** Constructor with InputStream. */
    public Simple1(java.io.InputStream stream) {
        this(stream, null);
    }
    /** Constructor with InputStream and supplied encoding */
    public Simple1(java.io.InputStream stream, String encoding) {
        if (jj_initialized_once) {
            System.out.println("ERROR: Second call to constructor of static parser.  ");
            System.out.println(
                    "	   You must either use ReInit() or set the JavaCC option STATIC to false");
            System.out.println("	   during parser generation.");
            throw new Error();
        }
        jj_initialized_once = true;
        try {
            jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source = new Simple1TokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 3; i++) jj_la1[i] = -1;
    }

    /** Reinitialise. */
    public static void ReInit(java.io.InputStream stream) {
        ReInit(stream, null);
    }
    /** Reinitialise. */
    public static void ReInit(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream.ReInit(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 3; i++) jj_la1[i] = -1;
    }

    /** Constructor. */
    public Simple1(java.io.Reader stream) {
        if (jj_initialized_once) {
            System.out.println("ERROR: Second call to constructor of static parser. ");
            System.out.println(
                    "	   You must either use ReInit() or set the JavaCC option STATIC to false");
            System.out.println("	   during parser generation.");
            throw new Error();
        }
        jj_initialized_once = true;
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new Simple1TokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 3; i++) jj_la1[i] = -1;
    }

    /** Reinitialise. */
    public static void ReInit(java.io.Reader stream) {
        if (jj_input_stream == null) {
            jj_input_stream = new SimpleCharStream(stream, 1, 1);
        } else {
            jj_input_stream.ReInit(stream, 1, 1);
        }
        if (token_source == null) {
            token_source = new Simple1TokenManager(jj_input_stream);
        }

        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 3; i++) jj_la1[i] = -1;
    }

    /** Constructor with generated Token Manager. */
    public Simple1(Simple1TokenManager tm) {
        if (jj_initialized_once) {
            System.out.println("ERROR: Second call to constructor of static parser. ");
            System.out.println(
                    "	   You must either use ReInit() or set the JavaCC option STATIC to false");
            System.out.println("	   during parser generation.");
            throw new Error();
        }
        jj_initialized_once = true;
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 3; i++) jj_la1[i] = -1;
    }

    /** Reinitialise. */
    public void ReInit(Simple1TokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 3; i++) jj_la1[i] = -1;
    }

    private static Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }

    /** Get the next Token. */
    public static final Token getNextToken() {
        if (token.next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        jj_gen++;
        return token;
    }

    /** Get the specific Token. */
    public static final Token getToken(int index) {
        Token t = token;
        for (int i = 0; i < index; i++) {
            if (t.next != null) t = t.next;
            else t = t.next = token_source.getNextToken();
        }
        return t;
    }

    private static int jj_ntk_f() {
        if ((jj_nt = token.next) == null)
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        else return (jj_ntk = jj_nt.kind);
    }

    private static java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
    private static int[] jj_expentry;
    private static int jj_kind = -1;

    /** Generate ParseException. */
    public static ParseException generateParseException() {
        jj_expentries.clear();
        boolean[] la1tokens = new boolean[5];
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 3; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1 << j)) != 0) {
                        la1tokens[j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.add(jj_expentry);
            }
        }
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = jj_expentries.get(i);
        }
        return new ParseException(token, exptokseq, tokenImage);
    }

    private static boolean trace_enabled;

    /** Trace enabled. */
    public static final boolean trace_enabled() {
        return trace_enabled;
    }

    /** Enable tracing. */
    public static final void enable_tracing() {}

    /** Disable tracing. */
    public static final void disable_tracing() {}
}