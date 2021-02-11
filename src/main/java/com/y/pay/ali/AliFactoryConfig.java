package com.y.pay.ali;

import com.alipay.easysdk.kernel.Config;
import com.y.pay.exception.AliRuntimeException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author XiFYuW
 * @since  2020/09/13 8:38
 */
@Component
public class AliFactoryConfig {


    public Config config(final String parentId) {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";

        Optional.ofNullable(null).ifPresent(x -> {
            // <-- 请填写您的AppId，例如：2019091767145019 -->
//             config.appId = users.getAppid();
            config.appId = "2021001195604194";
            // <-- 请填写您的应用私钥，例如：MIIEvQIBADANB ... ... -->
            config.merchantPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDKS4uD0h05V6j20+KgFG008hWbOjw+97q4Ghiw6UPMNTFEI6gw2cNKebKJLc4sTHiM/n5tuOeivLpLZ81kfMjf0CMkaboKrPcSoPSP8AZjhDqFziKlBt2tDJiKsbJ5J7nD5ZwRAyKtEG/PFvlRjYxH/Z/XlOB0ZLY2uiVmBvC+F1281luP8+8hXV5yVrv5NW+5rV0zJWMWochSIFl/ouCKhKlp+BFyDI+0klanBvIH5mWCIuHjgIwLoJvQcpLxb/YRPsizAshY8xZokNycSgbsaEG7Nc8gcDpoa6P1jR7ZNSJqw7zK27w8LeRB5KnOvNHdkrmhF8xXMDZGo3QfJ8rrAgMBAAECggEAcKgLhRixH6r6tKKhFYbqnk6my7Bttp9dQ4N89qChGgsRaaH122aaRFJro8t3guH/TH25yL7teIpjsPSYxHpdn46Y04FxktpqKdyq9bG0zRzpPnTihEbDksht9ucoviHqaPbop+y4FesOaSShKdKLKdhLeNI3AXrIosedZik9To5UQp5dDAJpttrxWJBIFO/6beIbm6d1S2Rg5BxPWAUlOvdSF1b9r0OAyEHQIm9KTUTcr/57TadexSPI+viWSctlw+JbJLr4AT4BIv31ArpgBZhLZTOrWOwFCANeYmUbAJlM2uZ7/uFqCSnkfqWEwyf7lC68+7/YvE+Y7MGtaCz9GQKBgQD4HXlMnFdQ/lS1jN4BAeK4MzYEPaXeXKpvHvf/KrvdUedgvGruUBM4hmkGwFBvl/KRN+z47V6cAVEkNxCuUIwg+F5V+v4bugxa2UNtfAITRS3HERfzWDRahMH9ysnUKN+SGOpvldUYxaNqE9K1/jMyHKNiBbOoEw3QWRZ4FpB7XwKBgQDQuU4QcWqmZAma8s0kgiRn9M+eY1AqWwX1CiE1ihosRmEqBVr99IxYbGxEg+u/7XNiWBAGGQmzSwb5S3iKVL7Fplb9Oqa9sSU2kbNhKbLSsYCLeWi30NoTKdZ0GAKgvCh+bMY1n2FpLWkc3YLWV8gYU/AZPVobnAOOAxSP8dHn9QKBgQCQDnTWEVP1F6XRPzfKt/CN6FXtgmmiwITIPd/ZjFNFPf+sP9mKAn4WbJvgKprJIBzbSfBR22f6Bh25fCs4OlYR8oK7Qx50tMHloY2vrLd7vsfEc91HEahcAgasNyiWASFFrye2n/T5DA9EULwfpLbO2qfWCt9zr5RuqrsMAHXk2wKBgEzFK5JetPR4bRQB5DRebdeSst619qFtk1ZaoHZZ0mCgjlJvX9VyokBPuM7GmuqgAsWUUGh9wxSgLvN4r+BpZTW7hdlkPjodMuvMUWcWeECIqxgh0sNxXRSZWdifkC/gpSBDD/BfK+wMmNHWI66E4QMZtIEEEOWAz8nnFiQPtL4VAoGBAL7zzgWwH2fTxHcAK9MqtN+tpZrwRBhTs7FJsi8r+IFNkPpDjrSVoaggVQp9fOK5ShvQsMAhJxxIxHBc/gCMh2nqfFrTi5gzn8T36pp/xN0Ckmf58EqK2JQAVbdJHiI11eRN3K368Jmj4OdSe+0JEfVbTv/dvUbcexUpDdyhgy4o";
//             config.merchantPrivateKey = users.getKeypath();
            // config.merchantPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCH3e1qWmNvuMBrYA/nQ5fYZNMbuDVNWxPYIvVWOP5CDVI2x9g1ynueofsjFTPDCFZAN0D9vPvWcupIvIjBOnkAFJBukf2OTBc6ttrpWKfowEeR1byDAieWPW09WeLMO3aRw5zvRiDLGPDVZnLwapn/dlBT1TvR2HA9f1rv45KPsjkBvSLnrEvi80QmF6I4cfhFP9RmPgelaoVy554P1Ei0DqtNkcJGKYTp/r/i5qLYHeru+T0BWgMoOtFc/eZczOIb3xDWOPqYa3ad+9HYRWPsgbLhWW0ziWbTo2i88mKlM0e4PmzIT4RqQTNgRBYM2SVZ6mmMkI75lZ5/VnpTcwi/AgMBAAECggEAcZ/PpVniPtSrVyEHK8kDfB0Vz+DF8uNwPRk/Q9R2DpdFYjrPPm0P5SJh/H0GxNx8BUgwOgOKOVXxSNMjKzXTa5XHFDFTc2V/J8M0YGGcSrJDKFsvRADRSnwkl2JMobA5AL4uyIxFcJRzFISRKy57i4FOvE1fsabodwXC70vKjhCj/XBCpQcFVr/dc698fv5dsQvUkpSYh41QWL7BqCbj3kviz0wGnu/cjrc5OTW5abzCsoBFfKsHU/W3zgGt3eaEpQYOOairaTs6bhs117e09nvcXWm9FJ9lFla7KeewcxhN4937yDZgwol15qKzFQ3RHf1T8AZaa5LjyfI78TojAQKBgQDxLdjJi2KtbjNgZmsIPlkdFJZ/bwikc/2MXSftbBV2siS3zT5JDYdaNpiGhy7LOcWQXxQm58Jo3Pg6SriCxps53LAGxKxzhi46+wANZepHqtcnMmkfeAciDe0mgaOvO4bIluwK87Jk58/ql/uLW2/YxEJJpbG5kOCUhMf80WpocQKBgQCQN1ezM/YEs7nHe5swDJSOSryDHv+tRaa0hqNa0CwGVYq8Qh2jPrs5PjyZA+k6mxC8/V2ht3bs6q5DgHV2RaXkuiUyWe8Auouvp4QQI1+41PZZfmh2sbKmkmO1Wxffavpm3BAh54LjFUigjnlfRiCEZJBjP8rxbNSBOyK7+BWcLwKBgQCIEsXtcKcMEtEJ4r8gt4yvy53d6FeH8alsOGL+oay6e2gcC8DIlsl3LLaBsGPGZaICAqLsEHavk3y55s6Telq6Grl2GCD0TRBoVpWA+a9lIA6kHt0Q1DeyDwluYFZp17wEEKK8yyIrrF6yjW1vAvfn5VNBGitb91G+eewbyEuxAQKBgFK2qyIo5y2DN0ZmaneAf6Os0boSiWoDzL01i3OGefmYDl4zPXRnglIuk+rfB2GJlmwOlSkvZJI1d4VXpAcVozQwXy0lz6KHW55/YYsv/rKv0is6CQgqmuBWjcFyYZrObJeaF9aaRcOgbWtIy+wM+JF2bCnAaNFXLfqAxEcy3pIZAoGAYnelOegK+x3mhX3GXbiZpTbifG9og+dOU7lTQYkWMxCtaJJzZuRt0vuPC8NbG08ziw5Mt+uy0en69DdRYowDFERXVTIRIWn4J8GLeMpR2BL7fgkKi0J4pUI0jDKTf9ipp+INyVqImCuH9VjQuyvbqG22MlhdEVEJOOhj2dFPsPg=";
            // 注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
            // <-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->
            //config.merchantCertPath = "";
            // <-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->
            //config.alipayCertPath = "";
            // <-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->
            //config.alipayRootCertPath = "";

            // 注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
            // <-- 请填写您的支付宝公钥，例如：MIIBIjANBg... -->
            config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA29M86naLvm71m/azdvZRlRLvTNzHmtFg7q9Oify+/l1J7bCCLxrOQL5SWfF6ITTQTeXcM8LDIsgyoCR7bWGi1sVq7FTcs5uJU49xdU5cJfyGyVQi++IQx1FImGm1XXBZTQAukDIdjYsUEseEAIqTFv2uJ/49pYhCsnwOfdAFwuTsF/M6piGR5C8F3VkLa/j1BZxlhohUal9E5cjhT5PTQGPOsm2B/+m8YJFzSn4GjXSv913nKXXmp0I+b4KPsEx/CBpP4t9+NEDi4FqsUYJubOm51GCtiWkDpV033wZCx6Bh4NCmxP/HcQutzveI3a3yPiFeqZwqaXvPsv1PZ3zY7QIDAQAB";
//             config.alipayPublicKey = users.getAlipubkey();
//            config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt3K4Euml9yw6fUh9tLdustns2uyodug7jizreUq36Xm8ZKSNpH4JGvpyzFe1t5eu2oGGzYfEjIQ0Yddc2sHwb9Lqaw5dEjhn0Ep6ZYoh0vJ7eBi+AmGm79FOpvIbf4JR+rtMra/LiC6N67+kfMePML48neL7bIS9BffybOhylTxQv6TS/5lnRugkc92w54xJCQnBSo55mNXpC69K9WaBsfiwf7QmkQEtapxg3bD2C1fFiTiUoLgUxi30Rc9t22nP/Lj2knoPAOqvCF6GOqgQBuLhpv09+E8T56j47cvjuWcVZU8wihhmZgJjscGpUhH5pg/lXLH4slF5Gdc9PHPK+wIDAQAB";
        });
        // 可设置异步通知接收服务地址（可选） <-- 请填写您的支付类接口异步通知接收服务地址，例如：https://www.test.com/callback -->
        config.notifyUrl = "";
        //可设置AES密钥，调用AES加解密相关接口时需要（可选） <-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->
        config.encryptKey = "";

        boolean is = config.alipayPublicKey == null || config.appId == null || config.merchantPrivateKey == null;
        if (is) {
            throw new AliRuntimeException("支付宝配置错误");
        }
        return config;
    }
}
