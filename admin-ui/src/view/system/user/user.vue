<template>
    <div>
      <Card class="card-search">
        <Form :model="searchParam" :label-width="80">
          <Row>
            <Col :lg="11" :md="24">
              <FormItem label="名称">
                <Input v-model="searchParam.name" style="width:200px" placeholder="名称" clearable></Input>
              </FormItem>
            </Col>
            <Col :lg="10" :md="24">
              <FormItem label="角色">
                <Select v-model="searchParam.roleId" style="width:200px" placeholder="角色" clearable>
                  <Option v-for="item in roleData" :value="item.roleId" :key="item.roleId">{{ item.roleName }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col :lg="3" :md="24" class=" card-search-search-btn-box" :label-width="10">
              <Button type="primary" @click="search">搜索</Button>
            </Col>
          </Row>
        </Form>
      </Card>
      <Card class="card-content">
        <p slot="title"
           class="card-content-title">
            <Row type="flex" justify="start" class="card-content-row">
              <Col span="12" class="card-content-add"  v-hasPermission="hasPermissions.add"><img @click="goToAdd" src="@/assets/images/icon-add.png"></Col>
              <Col span="12" v-hasPermission="hasPermissions.delete"><img @click="confDel" src="@/assets/images/icon-delete.png"></Col>
          </Row>
        </p>
        <div class="card-content-table">
          <Table border
            ref="selection"
            :loading="loading"
            :columns="colData"
            :data="listData"
            @on-selection-change="onChange">
          </Table>
        </div>
        <div class="card-content-pages">
          <Page :total="totalCount"
            show-elevator
            show-total
            :page-size="searchParam.limit"
            @on-change='change'
            @on-page-size-change='change'>
          </Page>
        </div>
      </Card>
      <user-add-update
        v-model="addUpdate"
        :title="addUPdateTitle"
        :roleData="roleData"
        @refreshDataList="initData"
        ref="addOrUpdate">
      </user-add-update>
      <usesr-detail
        v-model="showDetail"
        :roleData="roleData"
        ref="detailModal">
      </usesr-detail>
    </div>
</template>
<script>
import { inOf } from '@/libs/tools'
import userAddUpdate from '@/view/system/user/user-add-update'
import usesrDetail from '@/view/system/user/user-detail'
import { mapActions, mapState } from 'vuex'
export default {
  data () {
    return {
      searchParam: {
        page: 1,
        limit: 10
      },
      hasPermissions: {
        add: ['sys:user:save'],
        update: ['sys:user:update', 'sys:user:info'],
        delete: ['sys:user:delete'],
        detail: ['sys:user:info']
      },
      loading: true,
      addUpdate: false,
      showDetail: false,
      addUPdateTitle: '新增',
      roleData: [],
      colData: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '用户名',
          key: 'username',
          tooltip: true,
          align: 'center'
        },
        {
          title: '昵称',
          key: 'name',
          tooltip: true,
          align: 'center'
        },
        {
          title: '角色',
          key: 'roleNames',
          align: 'center',
          render: (h, params) => {
            let tags = []
            params.row.roleNameList.forEach(name => {
              let tag = h('Tag', { props: { color: 'success' } }, name)
              tags.push(tag)
            })
            return h('div', tags)
          }
        },
        {
          title: '状态',
          key: 'status',
          tooltip: true,
          align: 'center',
          render: (h, params) => {
            return h(
              'Tag',
              {
                props: {
                  color: params.row.status ? 'success' : 'error'
                }
              }, params.row.status ? '正常' : '锁定'
            )
          }
        },
        {
          title: '创建时间',
          key: 'createTime',
          tooltip: true,
          align: 'center'
        },
        {
          title: '更新时间',
          key: 'updateTime',
          tooltip: true,
          align: 'center'
        },
        {
          title: '操作',
          width: 180,
          align: 'center',
          render: (h, params) => {
            let buttons = []
            if (this.permissionList.length &&
                inOf(this.permissionList, this.hasPermissions.update)) {
              buttons.push(
                h(
                  'Button',
                  {
                    props: {
                      type: 'warning'
                    },
                    style: {
                      margin: '0 5px'
                    },
                    on: {
                      click: () => {
                        this.addUpdate = true
                        this.addUPdateTitle = '编辑'
                        this.$nextTick(() => {
                          this.$refs.addOrUpdate.init(params.row.userId)
                        })
                      }
                    }
                  }, '编辑'
                )
              )
            }
            if (this.permissionList.length &&
                inOf(this.permissionList, this.hasPermissions.detail)) {
              buttons.push(
                h(
                  'Button',
                  {
                    props: {
                      type: 'success'
                    },
                    style: {
                      margin: '0 5px'
                    },
                    on: {
                      click: () => {
                        this.showDetail = true
                        this.$nextTick(() => {
                          this.$refs.detailModal.init(params.row.userId)
                        })
                      }
                    }
                  }, '详情'
                )
              )
            }
            return h('div', buttons)
          }
        }
      ],
      listData: [],
      totalCount: 0,
      selectArry: []
    }
  },
  computed: {
    ...mapState({
      permissionList: state => state.router.permissions
    })
  },
  methods: {
    ...mapActions(['getUserList', 'getRoleSelect', 'deletes']),
    search () {
      this.searchParam.page = 1
      this.initData()
    },
    change (value) {
      this.searchParam.page = value
      this.initData()
    },
    onChange (value) {
      this.selectArry = value
    },
    goToAdd () {
      this.addUpdate = true
      this.addUPdateTitle = '新增'
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init()
      })
    },
    confDel () {
      if (this.selectArry.length > 0) {
        this.$Modal.confirm({
          title: '提示',
          content: '确认进行删除操作吗？',
          onOk: () => {
            this.deletes({ userIds: this.selectArry.map(item => item.userId) }).then(() => {
              this.initData()
              this.$Notice.success({
                title: '提示',
                desc: '删除成功'
              })
            }).catch(err => {
              this.$Notice.error({
                title: '错误',
                desc: err.msg
              })
            })
          },
          onCancel: () => {
            this.selectArry.length = 0
            this.$refs.selection.selectAll(false)
          }
        })
      } else {
        this.$Notice.warning({
          title: '警告',
          desc: '请至少选择一项'
        })
      }
    },
    initData () {
      this.loading = true
      this.getUserList(this.searchParam).then(res => {
        this.loading = false
        this.listData = res.data.page.list
        this.totalCount = res.data.page.totalCount
      }).catch(err => {
        this.loading = false
        this.$Notice.error({
          title: '错误',
          desc: err.msg
        })
      })
    },
    getSelectRole () {
      this.getRoleSelect().then(res => {
        this.roleData = res.data.list
      }).catch(err => {
        this.$Notice.error({
          title: '错误',
          desc: err.msg
        })
      })
    }
  },
  components: {
    userAddUpdate,
    usesrDetail
  },
  mounted () {
    this.initData()
    this.getSelectRole()
  }
}
</script>
<style lang="less">
  @import "~@/style/style.less";
</style>
