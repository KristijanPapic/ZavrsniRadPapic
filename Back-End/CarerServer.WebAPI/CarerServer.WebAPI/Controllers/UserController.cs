using AutoMapper;
using Carer.Model.DomainModels;
using Carer.Service.Common;
using CarerServer.WebAPI.Models.InsertModels;
using CarerServer.WebAPI.Models.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
namespace CarerServer.WebAPI.Controllers
{
    public class UserController :ApiController
    {
        private IUserService userService;
        private IMapper mapper;

        public UserController(IUserService userService,IMapper mapper)
        {
            this.userService = userService;
            this.mapper = mapper;
        }

        public async Task<HttpResponseMessage> GetAllUsersAsync()
        {
            List<UserViewModel> viewUsers = mapper.Map<List<UserDomainModel>, List<UserViewModel>>(await userService.GetAllUserAsync());
            return Request.CreateResponse(HttpStatusCode.OK, viewUsers);
        }
        public async Task<HttpResponseMessage> GetUserByIdAsync(Guid id)
        {
            UserViewModel viewUser = mapper.Map<UserDomainModel, UserViewModel>(await userService.GetUserByIdAsync(id));
            return Request.CreateResponse(HttpStatusCode.OK, viewUser);
        }

        public async Task<HttpResponseMessage> PutUserAsync(UserDomainModel user)
        {
            await userService.PutUserAsync(user);
            return Request.CreateResponse(HttpStatusCode.OK);
        }
        public async Task<HttpResponseMessage> DeleteUser(Guid id)
        {
            await userService.DeleteUserAsync(id);
            return Request.CreateResponse(HttpStatusCode.OK);
        }
    }
}