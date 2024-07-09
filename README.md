# Cryptography
Cryptography is the science of securing communication and information through the use of codes, so that only those for whom the information is intended can read and process it. It involves techniques and methods to transform readable data (plaintext) into an unreadable format (ciphertext) and vice versa, ensuring data privacy and integrity. 

## Key Concepts in Cryptography
1. **Encryption**: The process of converting plaintext into ciphertext using an algorithm and an encryption key. Encryption ensures that unauthorized parties cannot read the data.
   
2. **Decryption**: The process of converting ciphertext back into plaintext using a decryption key. This step allows the intended recipient to read the original data.

3. **Keys**: These are secret values used in the encryption and decryption processes. Keys must be kept secure; if a key is compromised, the encrypted data can be easily decrypted by unauthorized parties.

4. **Symmetric Cryptography**: Also known as secret-key cryptography, it uses the same key for both encryption and decryption. Examples include the Advanced Encryption Standard (AES) and Data Encryption Standard (DES).

5. **Asymmetric Cryptography**: Also known as public-key cryptography, it uses a pair of keys—one public and one private. The public key is used for encryption, and the private key is used for decryption. Examples include RSA and Elliptic Curve Cryptography (ECC).

6. **Hash Functions**: These are algorithms that take an input and produce a fixed-size string of bytes. The output, known as a hash value or digest, is unique to each unique input. Hash functions are used for data integrity checks. Examples include SHA-256 and MD5.

7. **Digital Signatures**: These are cryptographic techniques used to validate the authenticity and integrity of a message, software, or digital document. Digital signatures are created using the sender's private key and verified with the sender's public key.

## Characterize Cryptographic System By:
- Type of Encryption Operations Used
    - Substitution
    - Transposition
    - Product

- Number of Keys used
    - Single-key (private key)
    - Two-key (public key)

- Way in Which Plaintext is Processed
    - Block
    - Stream

