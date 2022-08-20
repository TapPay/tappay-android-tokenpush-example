package tech.cherri.tokenpushexample;

public class JSONUtils {

    public static String pretty(String unformattedString) {
        StringBuilder stringBuilder = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < unformattedString.length(); i++) {
            char letter = unformattedString.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    stringBuilder.append("\n" + indentString + letter + "\n");
                    indentString = indentString + "\t";
                    stringBuilder.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    stringBuilder.append("\n" + indentString + letter);
                    break;
                case ',':
                    stringBuilder.append(letter + "\n" + indentString);
                    break;

                default:
                    stringBuilder.append(letter);
                    break;
            }
        }

        return stringBuilder.toString();
    }
}
