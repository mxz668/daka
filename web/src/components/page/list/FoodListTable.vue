<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i>食品列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">
                    批量删除
                </el-button>
                <el-button type="primary" icon="add" class="handle-del mr10" @click="handleAdd">
                    新增食品
                </el-button>
                <el-select v-model="selectCate" placeholder="筛选类型" @change="selectCateChange" class="handle-select mr10">
                    <el-option label="全部" value=""></el-option>
                    <el-option key="1" label="配送" value="1"></el-option>
                    <el-option key="2" label="自提" value="2"></el-option>
                </el-select>
                <el-input v-model="select_word" placeholder="筛选关键词" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
            </div>
            <el-table :data="data" border class="table" ref="multipleTable">
                <el-table-column type="selection" width="40" align="center"></el-table-column>
                <el-table-column prop="foodName" label="食品名称" align="center"/>
                <el-table-column prop="cal" label="热量(焦)" align="center"/>
                <el-table-column prop="picId" label="图片" align="center" height="70">
                    <template slot-scope="scope">
                        <img :src="scope.row.pic.pic" @click="showPic(scope.row.pic.pic)" width="130" />
                    </template>
                </el-table-column>
                <el-table-column prop="price" label="价格" align="center"/>
                <el-table-column prop="foodClass.footClassName" label="食品等级" align="center"/>
                <el-table-column prop="foodDescribe" label="描述" align="center"/>
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
                <el-form-item label="食品名称">
                    <el-input v-model="form.foodName"></el-input>
                </el-form-item>
                <el-form-item label="热量(焦)">
                    <el-input v-model="form.cal"></el-input>
                </el-form-item>
                <el-form-item label="图片">
                    <el-upload
                            class="upload-demo"
                            action="http://localhost:8090/pic/upload/"
                            :before-upload="beforeAvatarUpload"
                            :on-remove="handleRemove"
                            :limit="1"
                            :file-list="pictureList"
                            list-type="picture">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2M</div>
                    </el-upload>
                </el-form-item>
                </el-form-item>
                <el-form-item label="价格">
                    <el-input v-model="form.price"></el-input>
                </el-form-item>
                <el-form-item label="食品等级">
                    <el-select v-model="foodClassValue" placeholder="请选择" @change="foodClassChange">
                        <el-option
                                v-for="item in foodClassList"
                                :key="item.id"
                                :label="item.footClassName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="form.foodDescribe"></el-input>
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
                <el-form-item label="食品名称">
                    <el-input v-model="form.foodName"></el-input>
                </el-form-item>
                <el-form-item label="热量(焦)">
                    <el-input v-model="form.cal"></el-input>
                </el-form-item>
                <el-form-item label="图片">
                    <el-upload
                            class="upload-demo"
                            action="http://localhost:8090/pic/upload/"
                            :before-upload="beforeAvatarUpload"
                            :on-remove="handleRemove"
                            :limit="1"
                            :file-list="pictureList"
                            list-type="picture">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2M</div>
                    </el-upload>
                </el-form-item>
                <el-form-item label="价格">
                    <el-input v-model="form.price"></el-input>
                </el-form-item>
                <el-form-item label="食品等级">
                    <el-select v-model="foodClassValue" placeholder="请选择" @change="foodClassChange">
                        <el-option
                                v-for="item in foodClassList"
                                :key="item.id"
                                :label="item.footClassName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="form.foodDescribe"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 查看图片 -->
        <el-dialog title="查看图片" :visible.sync="showPicture" width="60%" center>
            <span>
                <div width="80%">
                    <img :src="showPictureSrc" width="100%">
                </div>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="showPicture = false">取 消</el-button>
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
                url: '/food/queryFoodList',
                tableData: [],
                curPage: 1,
                totalCount:0,
                pageSize:10,
                multipleSelection: [],
                selectCate: '',
                select_word: '',
                del_list: [],
                pictureList : [],
                showPicture: false,
                showPictureSrc: "",
                foodClassList:[],
                foodClassValue:'',
                is_search: false,
                editVisible: false,
                addVisible: false,
                delVisible: false,
                showImg:false,
                form: {
                    id: "",
                    shopName:"",
                    address:"",
                    createTime:""
                },
                idx: -1,
                src :"",
                pic:{
                    picName:"",
                    picType:"",
                    pic:""
                }
            }
        },
        created() {
            this.getData();
            this.getFoodClassList();
        },
        computed: {
            data() {
                return this.tableData;
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
                // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
                if (process.env.NODE_ENV === 'development') {
                    this.url = '/food/queryFoodList';
                };
                this.$axios.post(this.url, {
                    currentPage: this.curPage,
                    pageSize : this.pageSize,
                    foodName : this.select_word
                }).then((res) => {
                    this.tableData = res.data.data.list;
                    this.totalCount = res.data.data.totalCount;
                })
            },
            getFoodClassList(){
                var url = '/food/queryFoodClassList'
                this.$axios.post(url).then((res) => {
                    this.foodClassList = res.data.data;
                })
            },
            search() {
                this.is_search = true;
                this.getData();
            },
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleAdd() {
                this.form = {
                    "foodName":"",
                    "cal":"",
                    "picId":"",
                    "price":"",
                    "foodClassId":"",
                    "foodDescribe":""
                }
                this.addVisible = true;
            },
            handleEdit(index, row) {
                this.pictureList = [];
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                    "id": item.id,
                    "foodName":item.foodName,
                    "cal":item.cal,
                    "picId":item.picId,
                    "price":item.price,
                    "foodDescribe":item.foodDescribe

                }
                var p = [{name: item.pic.picName, url: item.pic.pic}]
                this.pictureList = p;
                this.foodClassValue = item.foodClassId;
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
            // 新增
            saveAdd() {
                this.pictureList = [];
                var editUrl = "/food/add";
                this.$axios.post(editUrl, this.form).then((res) => {
                    if(res.rspCode == "000000"){
                        this.$message.success(`修改成功`);
                    }
                });
                // this.tableData.push(this.form);
                this.addVisible = false;
                this.getData();
            },
            foodClassChange(val){
                this.form.foodClassId = val;
            },
            selectCateChange(val){
                this.selectCate = val;
            },
            // 保存编辑
            saveEdit() {
                this.pictureList = [];
                this.$set(this.tableData, this.idx, this.form);
                this.editVisible = false;
                var editUrl = "/food/edit";
                this.$axios.post(editUrl, this.form).then((res) => {
                    if(res.rspCode == "000000"){
                        this.$message.success(`修改成功`);
                    }
                })
            },
            // 确定删除
            deleteRow(){
                this.delVisible = false;
                var editUrl = "/food/delete";
                const item = this.tableData[this.idx];
                this.$axios.post(editUrl, item).then((res) => {
                    if(res.rspCode == "000000"){
                        this.$message.success(`删除成功`);
                    }
                })
                this.tableData.splice(this.idx, 1);
                this.$message.success('删除成功');
            },
            //上传
            uploadPic(){
                return "/pic/upload";
            },
            handleRemove(){

            },
            beforeAvatarUpload(file){
                let _this = this;
                let reader = new FileReader();
                reader.readAsDataURL(file);

                _this.pic.pic = reader.result;
                _this.pic.picName = file.name;
                _this.pic.picType = file.type;

                var url = "/pic/insertOne";
                this.$axios.post(url, _this.pic).then((res) => {
                    this.form.picId = res.data.data;
                    if(res.rspCode === "000000"){
                        this.$message.success(`上传成功`);
                    }
                })
            },

            //图片加载
            getImageSrc(pid){
                var url = "/pic/queryById";
                var pic = {id:pid};
                this.$axios.post(url, pic).then((res) => {
                    this.src = res.data.data.pic;
                })
                return this.src;
            },
            showPic(src){
                this.showPictureSrc = src
                this.showPicture = true
            },

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
