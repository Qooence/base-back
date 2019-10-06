<template>
  <Modal :value="value"
    @on-ok="ok"
    @on-cancel="cancel"
    class="common-modal">
    <p class="add-title">{{title}}</p>
    <div class="form-content">
      <Form ref="addUpdateForm" :model="addUpdateForm" :rules="addUpdateRules" :label-width="80">
        <Card style="margin-bottom: 10px;">
          <p slot="title">角色信息</p>
          <FormItem prop="roleName" label="角色名">
            <Input type="text" v-model="addUpdateForm.roleName" placeholder="角色名" />
          </FormItem>
          <FormItem prop="remark" label="备注" >
            <Input v-model="addUpdateForm.remark" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="备注" />
          </FormItem>
        </Card>
        <Card class="per-card">
          <p slot="title">分配权限</p>
          <Tree :data="treeData" show-checkbox ref="tree"></Tree>
        </Card>
      </Form>
    </div>
    <div slot="footer">
      <Button @click="cancel" type="info">取消</Button>
      <Button @click="ok" type="success">提交</Button>
    </div>
  </Modal>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  name: 'userAddUpdate',
  data () {
    return {
      addUpdateForm: {
        roleName: '',
        menuIdList: '',
        remark: ''
      },
      addUpdateRules: {
        roleName: [
          {
            required: true, message: '角色名不能为空', trigger: 'blur'
          }
        ]
      },
      treeData: []
    }
  },
  props: {
    value: {
      type: Boolean,
      required: true,
      default: false
    },
    title: {
      type: String,
      default: '新增'
    },
    roleData: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    ...mapActions(['saveRole', 'updateRole', 'getRoleById', 'getRouteTree']),
    init (roleId) {
      if (roleId) {
        this.getRoleById({ roleId }).then(res => {
          this.addUpdateForm.roleId = res.data.role.roleId
          this.addUpdateForm.roleName = res.data.role.roleName
          this.addUpdateForm.remark = res.data.role.remark
          this.treeData = res.data.tree
        }).catch(err => {
          this.$Notice.error({
            title: '错误',
            desc: err.msg
          })
        })
      } else {
        this.getRouteTree().then(res => {
          this.treeData = res.data.tree
        })
      }
      this.$nextTick(() => {
        this.$refs.addUpdateForm.resetFields()
      })
    },
    cancel () {
      this.clearData()
      this.$emit('input', !this.value)
    },
    ok () {
      this.$refs.addUpdateForm.validate((valid) => {
        if (valid) {
          this.addUpdateForm.menuIdList = this.$refs.tree.getCheckedAndIndeterminateNodes().map(item => item.id)
          this.addUpdateForm.checkes = this.$refs.tree.getCheckedNodes().map(item => item.id).join(',')
          console.log(this.addUpdateForm)
          let oper = this.addUpdateForm.roleId ? 'updateRole' : 'saveRole'
          this[oper](this.addUpdateForm).then(res => {
            if (res.data.code === 0) {
              this.$emit('input', !this.value)
              this.$emit('refreshDataList')
              this.clearData()
              this.$Notice.success({
                title: '成功',
                desc: '操作成功'
              })
            }
          }).catch(err => {
            this.$Notice.error({
              title: '错误',
              desc: err.msg
            })
          })
        } else {
          this.$Notice.warning({
            title: '警告',
            desc: '请完善信息'
          })
        }
      })
    },
    clearData () {
      this.treeData = []
      this.addUpdateForm = {}
    }
  }
}
</script>
<style lang="less">
  @import "~@/style/modal.less";
  .common-modal .form-content{
    padding: 20px 50px 20px 50px;
  }
  .per-card{
    height: 300px;
    overflow-x: hidden;
    overflow-y: auto;
    line-height: 18px;
  }
</style>
