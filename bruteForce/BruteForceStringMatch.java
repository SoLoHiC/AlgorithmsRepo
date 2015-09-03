public class BruteForceStringMatch {
    public static void main(String[] args) {
        String text = "aosdfioweifonisdfanwefoaonsdifassdasdfasdfasdfoidfnoanfisdfnalskjdfoalsdmocansodinaioasdfasdoaasdicnansif";
        String pattern = "nansif";

        System.out.println("text:" + text + "\npattern:" + pattern);

        System.out.println("matched at:" + bruteForceStringMatch(text, pattern));
    }

    public static int bruteForceStringMatch(String text, String pattern) {
        for(int i = 0; i < text.length() - pattern.length() + 1; i++) {
            int j = 0;
            while(j < pattern.length() && text.charAt(i+j) == pattern.charAt(j)) {
                j++;
            }
            if(j == pattern.length()) {
                return i;
            }
        }
        return -1;
    }
}
