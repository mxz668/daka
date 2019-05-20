<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 售后列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <!--<el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">-->
                    <!--批量删除-->
                <!--</el-button>-->
                <el-button type="primary" icon="add" class="handle-del mr10" @click="handleAdd">
                    新增
                </el-button>
                <!--<el-select v-model="select_cate" placeholder="筛选类型" class="handle-select mr10">-->
                    <!--<el-option key="1" label="配送" value="1"></el-option>-->
                    <!--<el-option key="2" label="自提" value="2"></el-option>-->
                <!--</el-select>-->
                <el-input v-model="select_word" placeholder="筛选关键词" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
            </div>
            <el-table :data="data" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="40" align="center"></el-table-column>
                <el-table-column prop="shopName" label="客户账号" align="center"/>
                <el-table-column prop="address" label="客服名称" align="center"/>
                <el-table-column prop="address" label="售后原因" align="center"/>
                <el-table-column prop="address" label="处理结果" align="center"/>
                <el-table-column prop="createTime" :formatter="dateFormat" label="创建时间"/>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
                <el-form-item label="商铺名称">
                    <el-input v-model="form.shopName"></el-input>
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
                <el-form-item label="商铺名称">
                    <el-input v-model="form.shopName"></el-input>
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
        name: 'basetable',
        data() {
            return {
                url: '/shop/queryShopList',
                tableData: [],
                curPage: 1,
                totalCount:0,
                pageSize:10,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                editVisible: false,
                addVisible: false,
                delVisible: false,
                form: {
                    id: "",
                    shopName:"",
                    address:"",
                    createTime:""
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
                this.cur_page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
                if (process.env.NODE_ENV === 'development') {
                    this.url = '/shop/queryShopList';
                };
                this.$axios.post(this.url, {
                    currentPage: this.curPage,
                    pageSize : this.pageSize,
                    shopName : this.select_word
                }).then((res) => {
                    this.tableData = res.data.data.list;
                    this.totalCount = res.data.data.totalCount;
                })
            },
            search() {
                this.is_search = true;
                this.getData();
            },
            dateFormat(row, column) {
                var date = new Date(row.createTime) //时间戳为10位需*1000，时间戳为13位的话不需乘1000
                var Y = date.getFullYear() + '-'
                var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'
                var D = date.getDate() + ' '
                var h = date.getHours() + ':'
                var m = date.getMinutes() == 0 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':'
                var s = date.getSeconds() == 0 ? '0' + date.getSeconds() : date.getSeconds()
                return Y+M+D+h+m+s
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleAdd() {
                this.form = {
                    "shopName":"",
                    "address":"",
                }
                this.addVisible = true;
            },
            handleEdit(index, row) {
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                    "id": item.id,
                    "shopName":item.shopName,
                    "address":item.address
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
                var editUrl = "/shop/add";
                this.$axios.post(editUrl, this.form).then((res) => {
                    if(res.rspCode == "000000"){
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
                var editUrl = "/shop/edit";
                debugger
                this.$axios.post(editUrl, this.form).then((res) => {
                    if(res.rspCode == "000000"){
                        this.$message.success(`修改成功`);
                    }
                })
            },
            // 确定删除
            deleteRow(){
                this.delVisible = false;
                var editUrl = "/shop/delete";
                const item = this.tableData[this.idx];
                this.$axios.post(editUrl, item).then((res) => {
                    if(res.rspCode == "000000"){
                        this.$message.success(`删除成功`);
                    }
                })
                this.tableData.splice(this.idx, 1);
                this.$message.success('删除成功');
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
