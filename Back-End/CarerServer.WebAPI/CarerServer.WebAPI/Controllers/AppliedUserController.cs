using AutoMapper;
using Carer.Common;
using Carer.Model.DomainModels;
using Carer.Service;
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
    public class AppliedUserController : ApiController
    {
        private IAppliedUserService appliedUserService;
        private IMapper mapper;

        public AppliedUserController(IAppliedUserService appliedUserService, IMapper mapper)
        {
            this.appliedUserService = appliedUserService;
            this.mapper = mapper;
        }

        public async Task<HttpResponseMessage> GetAllAppliedUsersAsync([FromUri]AppliedUserFilter filter)
        {
            List<AppliedUserViewModel> viewAppliedUsers = mapper.Map<List<AppliedUserDomainModel>, List<AppliedUserViewModel>>(await appliedUserService.GetAllAppliedUserAsync(filter));
            return Request.CreateResponse(HttpStatusCode.OK, viewAppliedUsers);
        }

        public async Task<HttpResponseMessage> PostAppliedUser([FromBody]AppliedUserInputModel appliedUser)
        {

            await appliedUserService.PostAppliedUserAsync(mapper.Map<AppliedUserInputModel, AppliedUserDomainModel>(appliedUser));
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        public async Task<HttpResponseMessage> PutAppliedUser([FromUri] Guid adId, Guid userId, string status)
        {
            await appliedUserService.PutAppliedUserAsync(userId, adId, status);
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        public async Task<HttpResponseMessage> DeleteAppliedUser(Guid userId,Guid adId)
        {
            await appliedUserService.DeleteAppliedUserAsync(userId,adId);
            return Request.CreateResponse(HttpStatusCode.OK);
        }
    }
}