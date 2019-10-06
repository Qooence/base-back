<template>
  <Modal :value="value"
    @on-cancel="cancel"
    class="common-modal">
    <p class="add-title">详情</p>
    <div class="form-content">
      <Form :label-width="80">
        <FormItem  label="用户名：">
          {{ detailForm.username }}
        </FormItem>
        <FormItem label="昵称：">
          {{ detailForm.name }}
        </FormItem>
        <FormItem label="角色：">
          {{ detailForm.roleIdList.join(',') }}
        </FormItem>
        <FormItem label="状态：">
          {{ detailForm.status }}
        </FormItem>
        <FormItem label="创建时间：">
          {{ detailForm.createTime }}
        </FormItem>
      </Form>
    </div>
    <div slot="footer">
      <Button @click="cancel" type="success">关闭</Button>
    </div>
  </Modal>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  name: 'userAddUpdate',
  data () {
    return {
      detailForm: {
        username: '',
        name: '',
        roleIdList: [],
        status: 1,
        createTime: ''
      }
    }
  },
  props: {
    value: {
      type: Boolean,
      required: true,
      default: false
    },
    roleData: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    ...mapActions(['getUserById']),
    init (userId) {
      this.getUserById({ userId }).then(res => {
        this.detailForm.userId = res.data.user.userId
        this.detailForm.username = res.data.user.username
        this.detailForm.salt = res.data.user.salt
        this.detailForm.name = res.data.user.name
        res.data.user.roleIdList.forEach(item => {
          this.roleData.forEach(r => {
            if (r.roleId === item) {
              this.detailForm.roleIdList.push(r.roleName)
            }
          })
        })
        this.detailForm.status = res.data.user.status ? '正常' : '锁定'
        this.detailForm.createTime = res.data.user.createTime
      }).catch(err => {
        this.$Notice.error({
          title: '错误',
          desc: err.msg
        })
      })
    },
    cancel () {
      this.detailForm.roleIdList.length = 0
      this.$emit('input', !this.value)
    }
  }
}
</script>
<style lang="less">
  @import "~@/style/modal.less";
</style>
