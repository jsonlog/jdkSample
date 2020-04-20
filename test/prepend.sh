
# cd /test/java/lang/Class/forName/
lsdir()
{
    for dir in `ls $1`
    do
        if [[ -d $1/$dir ]];then
            lsdir $1/$dir
        elif [[ $dir == *.java ]];then
            grepresult=`grep -c "package " $1/$dir`
            if [[ $grepresult -eq '0' ]];then
                pwdpwd=$1
                package=${pwdpwd/'/home/dida/push/jdkSamples/'/''}
                # package=${package/".java"/""}
                package='package '${package//"/"/"."}';'
                echo $package
                sed -i "1 s/^/${package}\n/" $1/$dir
            fi
        fi
    done

}
lsdir $1
# /home/dida/push/jdkSamples/test/java/lang/Class/forName