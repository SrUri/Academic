BEGIN {FS=":"};
{
subtotal = $1*$2;
print "Subtotal per "$3" = "subtotal;
total = total + subtotal
}
END {print "Total = " total}

