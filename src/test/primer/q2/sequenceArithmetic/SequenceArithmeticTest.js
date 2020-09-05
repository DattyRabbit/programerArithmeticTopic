var date1=new Date();  //开始时间
var op = ["*", ""];
for (i = 1000; i < 10000; i++) {
    var c = String(i);
    for (j = 0; j < op.length; j++) {
        for (k = 0; k < op.length; k++) {
            for (l = 0; l < op.length; l++) {
                val = c.charAt(3) + op[j] + c.charAt(2) + op[k] + c.charAt(1) + op[l] + c.charAt(0);
                if (val.length > 4) { /* 一定要插入1 个运算符 */
                    if (i == eval(val)) {
                        console.log(val + " = " + i);
                    }
                }
            }
        }
    }
}

var date2=new Date();    //结束时间
var date3=date2.getTime()-date1.getTime()  //时间差的毫秒数
console.log("耗时" + date3 + " ms ");