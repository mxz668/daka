<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 订单列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="add" class="handle-del mr10" @click="handleAdd">新增</el-button>
                <el-select v-model="selectCate" placeholder="筛选类型" @change="selectCateChange" class="handle-select mr10">
                    <el-option label="全部" value=""></el-option>
                    <el-option key="1" label="配送" value="1"></el-option>
                    <el-option key="2" label="自提" value="2"></el-option>
                </el-select>
                <el-input v-model="select_word" placeholder="客户账号" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
            </div>
            <el-table :data="data" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="40" align="center"></el-table-column>
                <el-table-column prop="orderSeq" label="订单号" align="center" width="120"/>
                <el-table-column prop="consumerName" label="客户账号" align="center" width="100"/>
                <el-table-column prop="mark" label="备注" align="center"/>
                <el-table-column prop="pic" label="设计图" align="center">
                    <template slot-scope="scope">
                        <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558174630951&di=8f560cc02eb9d2ba9148720f2bde213b&imgtype=0&src=http%3A%2F%2Fs8.sinaimg.cn%2Fmw690%2F0066GGChgy6UddzyM1Fe7" width="130" />
                    </template>
                </el-table-column>
                <el-table-column prop="price" label="价格" align="center"/>
                <el-table-column prop="address" label="地址" align="center"/>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <!--<el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :page-size="pageSize" :total="totalCount">
                </el-pagination>
            </div>
        </div>
        <!-- 新增弹出框 -->
        <el-dialog title="新增" :visible.sync="addVisible" width="40%">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="订单号">
                    <el-input v-model="form.orderSeq"></el-input>
                </el-form-item>
                <el-form-item label="客户账号">
                    <el-input v-model="form.consumerName"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input type="textarea" v-model="form.mark"></el-input>
                </el-form-item>
                <el-form-item label="设计图">
                    <el-input v-model="form.pic"></el-input>
                </el-form-item>
                <el-form-item label="价格">
                    <el-input v-model="form.price"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAdd">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="订单号">
                    <el-input v-model="form.orderSeq"></el-input>
                </el-form-item>
                <el-form-item label="客户账号">
                    <el-input v-model="form.consumerName"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input type="textarea" v-model="form.mark"></el-input>
                </el-form-item>
                <el-form-item label="设计图">
                    <el-input v-model="form.pic"></el-input>
                </el-form-item>
                <el-form-item label="价格">
                    <el-input v-model="form.price"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                url: '/order/queryOrderList',
                tableData: [],
                curPage: 1,
                totalCount:0,
                pageSize:10,
                multipleSelection: [],
                selectCate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                editVisible: false,
                addVisible: false,
                delVisible: false,
                form: {
                    id: "",
                    orderSeq:"",
                    consumerName:"",
                    mark: "",
                    pic: "",
                    price: "",
                    address: ""
                },
                idx: -1
            }
        },
        created() {
            this.getData();
        },
        computed: {
            data() {
                return this.tableData.filter((d) => {
                    let is_del = false;
                    for (let i = 0; i < this.del_list.length; i++) {
                        if (d.name === this.del_list[i].name) {
                            is_del = true;
                            break;
                        }
                    }
                    if (!is_del) {
                        return d;
                    }
                })
            }
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.curPage = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.url = '/order/queryOrderList';
                this.$axios.post(this.url, {
                    currentPage: this.curPage,
                    pageSize : this.pageSize,
                    consumerName : this.select_word
                }).then((res) => {
                    debugger
                    this.tableData = res.data.respData;
                    // this.totalCount = res.data.data.totalCount;
                })
            },
            search() {
                this.is_search = true;
                this.getData();
            },
            dateFormat(row, column) {
                var date = new Date(row.deliveryDate) //时间戳为10位需*1000，时间戳为13位的话不需乘1000
                var Y = date.getFullYear() + '-'
                var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'
                var D = date.getDate() + ' '
                var h = date.getHours() + ':'
                var m = date.getMinutes() == 0 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':'
                var s = date.getSeconds() == 0 ? '0' + date.getSeconds() : date.getSeconds()
                return Y+M+D+h+m+s
            },
            converse(row, column) {
                var retName = "";
                if(row.delivery == '1'){
                    retName = "配送"
                }else {
                    retName = "自提"
                }
                return retName
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleAdd() {
                this.form = {
                    "orderSeq": "",
                    "consumerName": "",
                    "mark": "",
                    "pic": "",
                    "price": "",
                    "address": "",
                }
                this.addVisible = true;
            },
            handleEdit(index, row) {
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                    "id": item.id,
                    "orderSeq": item.orderSeq,
                    "consumerName": item.consumerName,
                    "mark": item.mark,
                    "price": item.price,
                    "address": item.address,
                    "status": item.status
                }
                this.editVisible = true;
            },
            handleDelete(index, row) {
                this.idx = index;
                this.delVisible = true;
            },
            delAll() {
                const length = this.multipleSelection.length;
                let str = '';
                this.del_list = this.del_list.concat(this.multipleSelection);
                for (let i = 0; i < length; i++) {
                    str += this.multipleSelection[i].name + ' ';
                }
                this.$message.error('删除了' + str);
                this.multipleSelection = [];
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // 新增
            saveAdd() {
                var editUrl = "/order/addOrder";
                this.$axios.post(editUrl, this.form).then((res) => {
                    if(res.rspCode == "0000"){
                        this.$message.success(`修改成功`);
                    }
                });
                this.tableData.push(this.form);
                this.addVisible = false;
            },
            // 保存编辑
            saveEdit() {
                this.$set(this.tableData, this.idx, this.form);
                this.editVisible = false;
                var editUrl = "/order/editOrder";
                this.$axios.post(editUrl, this.form).then((res) => {
                    if(res.rspCode == "0000"){
                        this.$message.success(`修改成功`);
                    }
                })
            },
            selectCateChange(val){
                this.selectCate = val;
            },
            // 确定删除
            deleteRow(){
                this.tableData.splice(this.idx, 1);
                this.$message.success('删除成功');
                this.delVisible = false;
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .table{
        width: 100%;
        font-size: 14px;
    }
    .red{
        color: #ff0000;
    }
    .mr10{
        margin-right: 10px;
    }
</style>
