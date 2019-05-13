
/**
 * 时间戳转日期
 */
export function timestampFormat(val) {
    if(val.length == 10){//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        val = val * 10;
    }
    var date = new Date(val);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = date.getDate() + ' ';
    var h = date.getHours() + ':';
    var m = date.getMinutes() == 0 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
    var s = date.getSeconds() == 0 ? '0' + date.getSeconds() : date.getSeconds();
    return Y+M+D+h+m+s
}