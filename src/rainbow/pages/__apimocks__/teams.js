module.exports = {
  path: "/api/team",
  method: "get",
  delay: 2000,
  response: {
    code: 200,
    msg: "",
    data: [{ name: "项目一", progress: 80 }, { name: "项目二", progress: 20 }]
  }
}
