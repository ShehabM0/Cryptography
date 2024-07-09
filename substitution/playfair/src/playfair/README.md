# Playfair
- The best-known multiple-letter encryption cipher, which treats digrams in the plaintext as single units and translates these units into ciphertext digrams.
- The algorithm is based on the use of a 5 × 5 matrix of letters constructed using a keyword.

## Matrix construction
- the matrix is constructed by filling in the letters of the keyword (minus duplicates) from left to right.
- then filling in the remainder of the matrix with the remaining letters in alphabetic order.
- the letters I and J count as one letter.

## Plaintext is encrypted two letters at a time
- if a pair is a repeated letter, insert filler like 'X’.
- if both letters fall in the same row, replace each with letter to right (wrapping back to start from end).
- if both letters fall in the same column, replace each with the letter below it (wrapping to top from bottom).
- otherwise each letter is replaced by the letter in the same row and in the column of the other letter of the pair.

## Ciphertext is decrypted two letters at a time
- if a pair is a repeated letter, insert filler like 'X’.
- if both letters fall in the same row, replace each with letter to left (wrapping back to start from begin).
- if both letters fall in the same column, replace each with the letter upove it (wrapping to bottom from top).
- otherwise each letter is replaced by the letter in the same row and in the column of the other letter of the pair.
