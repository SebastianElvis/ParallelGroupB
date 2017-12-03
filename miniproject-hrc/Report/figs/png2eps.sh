rm *.eps
for x in `ls`
do
y=$(basename $x .png);echo $y
convert $x $y".eps"
done
