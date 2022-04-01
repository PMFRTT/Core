package core.currency;

public class Utils {

    /*public static String formatCurrencyString(double f, Currency currency) {
        if (currency == Currency.USD) {
            return new DecimalFormat("0.00").format(f);
        }
        return new DecimalFormat("0.######").format(f);
    }*/

    public static String getSkullURL(Integer value) {
        return switch (value) {
            case 0 -> "http://textures.minecraft.net/texture/6d68343bd0b129de93cc8d3bba3b97a2faa7ade38d8a6e2b864cd868cfab";
            case 1 -> "http://textures.minecraft.net/texture/d2a6f0e84daefc8b21aa99415b16ed5fdaa6d8dc0c3cd591f49ca832b575";
            case 2 -> "http://textures.minecraft.net/texture/96fab991d083993cb83e4bcf44a0b6cefac647d4189ee9cb823e9cc1571e38";
            case 3 -> "http://textures.minecraft.net/texture/cd319b9343f17a35636bcbc26b819625a9333de3736111f2e932827c8e749";
            case 4 -> "http://textures.minecraft.net/texture/d198d56216156114265973c258f57fc79d246bb65e3c77bbe8312ee35db6";
            case 5 -> "http://textures.minecraft.net/texture/7fb91bb97749d6a6eed4449d23aea284dc4de6c3818eea5c7e149ddda6f7c9";
            case 6 -> "http://textures.minecraft.net/texture/9c613f80a554918c7ab2cd4a278752f151412a44a73d7a286d61d45be4eaae1";
            case 7 -> "http://textures.minecraft.net/texture/9e198fd831cb61f3927f21cf8a7463af5ea3c7e43bd3e8ec7d2948631cce879";
            case 8 -> "http://textures.minecraft.net/texture/84ad12c2f21a1972f3d2f381ed05a6cc088489fcfdf68a713b387482fe91e2";
            case 9 -> "http://textures.minecraft.net/texture/9f7aa0d97983cd67dfb67b7d9d9c641bc9aa34d96632f372d26fee19f71f8b7";
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    public static int getOffset(Integer value){
        return switch (value){
            case 9 -> 0;
            case 10 -> 1;
            case 11 -> 2;
            case 12 -> 3;
            case 14 -> 4;
            case 15 -> 5;
            case 16 -> 6;
            case 17 -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

}
