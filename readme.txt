openssl genrsa -out rsa_1024_priv.pem 1024
cat rsa_1024_priv.pem
openssl rsa -pubout -in rsa_1024_priv.pem -out rsa_1024_pub.pem
cat rsa_1024_pub.pem
-- Java Convert
##Convert private Key to PKCS#8 format (so Java can read it)
openssl pkcs8 -topk8 -inform PEM -outform DER -in rsa_1024_priv.pem -out private_key.der -nocrypt
##Output public key portion in DER format (so Java can read it)
openssl rsa -in rsa_1024_priv.pem -pubout -outform DER -out public_key.der
