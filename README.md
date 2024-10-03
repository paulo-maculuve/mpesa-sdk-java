
# M-Pesa SDK Java

Este pacote permite interagir com a API do M-Pesa, facilitando transações como C2B, B2B, B2C, e consultas de transações. Ele também oferece suporte para transações de reversão.

## Instalação

Para instalar esta dependência, basta adicionar o comando abaixo ao pom.xml:
```xml
<dependency>
  <groupId>mz.co.maculuve</groupId>
  <artifactId>mpesa-sdk-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Configuração

Antes de utilizar o SDK, você deve configurar as credenciais e parâmetros da API do M-Pesa. O pacote permite que você defina as seguintes variáveis:

- **`apiKey`** — Sua chave de API fornecida pela M-Pesa.
- **`publicKey`** — Chave pública utilizada para encriptação das requisições.
- **`environment`** — O ambiente de execução da API, que pode ser:
  - `'development'` para o ambiente de desenvolvimento (sandbox).
  - `'production'` para o ambiente de produção.
- **`serviceProviderCode`** — Código do provedor de serviço. O valor padrão é `'171717'`.
- **`origin`** — Origem da aplicação, usada para validar as requisições. Por padrão já foi definido `'developer.mpesa.vm.co.mz'`
- **`initiatorIdentifier`** — Identificador do iniciador, que autoriza as transações.
- **`securityCredential`** — Credencial de segurança encriptada, utilizada para verificar a identidade do iniciador.

### Exemplo de Uso

```java
import mz.co.maculuve.helpers.Config;

public class Main {
    public static void main(String[] args) {
        // Configuração da API M-Pesa
        Config.config(
            "development", // ou "production"
            "your-api-key",
            "your-public-key",
            "171717", // Para teste
            "your-initiator-id",
            "your-security-credential"
        );

        // Exemplo de acesso às configurações
        System.out.println("Environment: " + Config.getEnvironment());
        System.out.println("Origin: " + Config.getOrigin());
        // ... outros getters conforme necessário
    }
}
```

### C2B
- A Chamada API C2B é utilizada como uma transação normal entre clientes e empresas. Os fundos da carteira de dinheiro móvel do cliente serão deduzidos e transferidos para a carteira de dinheiro móvel da empresa. Para autenticar e autorizar esta transação, a M-Pesa Payments Gateway iniciará uma mensagem USSD Push para o cliente para recolher e verificar o número PIN do dinheiro móvel. Este número não é armazenado e é utilizado apenas para autorizar a transação.

```java
import mz.co.maculuve.Mpesa;

// Configuração da API M-Pesa
Mpesa.config(
    environment: "development", // ou "production"
    apiKey: "your-api-key",
    publicKey: "your-public-key",
    serviceProviderCode: "171717",
    initiatorIdentifier: "your-initiator-id",
    securityCredential: "your-security-credential"
);

String transactionReference = generateShortUUID(); 
String thirdPartyReference = generateShortUUID();
Transaction response = Mpesa.c2b(1, "258846568447", transactionReference, thirdPartyReference);

System.out.println(response.toString());
```

### B2C
- A Chamada API B2C é utilizada como uma transação normal entre empresas e clientes. Os fundos da carteira de dinheiro móvel da empresa serão deduzidos e transferidos para a carteira de dinheiro móvel do cliente terceiro.

```java
import mz.co.maculuve.Mpesa;

// Configuração da API M-Pesa
Mpesa.config(
        environment: "development", // ou "production"
        apiKey: "your-api-key",
        publicKey: "your-public-key",
        serviceProviderCode: "171717",
        initiatorIdentifier: "your-initiator-id",
        securityCredential: "your-security-credential"
);

String transactionReference = generateShortUUID(); 
String thirdPartyReference = generateShortUUID();
Transaction response = Mpesa.b2c(1, "258846568447", transactionReference, thirdPartyReference);

System.out.println(response.toString());
```

### B2B
- A Chamada API B2B é utilizada como uma transação normal entre empresas. Os fundos da carteira de dinheiro móvel da empresa serão deduzidos e transferidos para a carteira de dinheiro móvel da empresa terceira.

```java
import mz.co.maculuve.Mpesa;

// Configuração da API M-Pesa
Mpesa.config(
        environment: "development", // ou "production"
        apiKey: "your-api-key",
        publicKey: "your-public-key",
        serviceProviderCode: "171717",
        initiatorIdentifier: "your-initiator-id",
        securityCredential: "your-security-credential"
);

String transactionReference = generateShortUUID(); 
String thirdPartyReference = generateShortUUID();
Transaction response = Mpesa.b2b(1, "258846568447", transactionReference, thirdPartyReference);

System.out.println(response.toString());
```

### Transaction
- A API Consultar estado da transação é utilizada para determinar o estado atual de uma determinada transação. Utilizando a ID da transação ou a ID da conversação da transação da plataforma de dinheiro móvel, o gateway de pagamentos M-Pesa devolverá informações sobre o estado da transação.

```java
import mz.co.maculuve.Mpesa;

// Configuração da API M-Pesa
Mpesa.config(
        environment: "development", // ou "production"
        apiKey: "your-api-key",
        publicKey: "your-public-key",
        serviceProviderCode: "171717",
        initiatorIdentifier: "your-initiator-id",
        securityCredential: "your-security-credential"
);

String transactionReference = generateShortUUID(); 
String thirdPartyReference = generateShortUUID();
Transaction response = Mpesa.transaction(transactionReference, thirdPartyReference);

System.out.println(response.toString());
```

### Reversal
- A API de reversão é utilizada para reverter uma transação bem sucedida. Utilizando o ID da transação de uma transação anterior bem sucedida, o Portal de Pagamentos M-Pesa retira os fundos da carteira de dinheiro móvel do destinatário e reverte os fundos para a carteira de dinheiro móvel da parte que iniciou a transação original.

```java
import mz.co.maculuve.Mpesa;

// Configuração da API M-Pesa
Mpesa.config(
        environment: "development", // ou "production"
        apiKey: "your-api-key",
        publicKey: "your-public-key",
        serviceProviderCode: "171717",
        initiatorIdentifier: "your-initiator-id",
        securityCredential: "your-security-credential"
);

String transactionReference = generateShortUUID(); 
String thirdPartyReference = generateShortUUID();
Transaction response = Mpesa.reversal(1, transactionReference, thirdPartyReference);

System.out.println(response.toString());
```

## Requisitos
- **`Java 17`**

```java
import mz.co.maculuve.helpers.Config;
import mz.co.maculuve.Mpesa;
import mz.co.maculuve.Transaction;

import java.util.UUID;

public class Test {

    public static String generateShortUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").substring(0, 7);
    }

    public static void main(String[] args) throws Exception {
        Mpesa mpesa = new Mpesa();
        
    }
}
```
