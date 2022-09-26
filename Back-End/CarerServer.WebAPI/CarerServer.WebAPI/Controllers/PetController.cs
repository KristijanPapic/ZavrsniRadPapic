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
    public class PetController : ApiController
    {
        private IPetService petService;
        private IMapper mapper;

        public PetController(IPetService petService, IMapper mapper)
        {
            this.petService = petService;
            this.mapper = mapper;
        }

      
        public async Task<HttpResponseMessage> GetPetsAsync([FromUri]PetFilter filter = null)
        {
            List<PetViewModel> pets = mapper.Map<List<PetDomainModel>, List<PetViewModel>>(await petService.GetAllPetAsync(filter));
            return Request.CreateResponse(HttpStatusCode.OK, pets);
        }
        public async Task<HttpResponseMessage> PostPet(PetInputModel pet)
        {

            await petService.PostPetAsync(mapper.Map<PetInputModel,PetDomainModel>(pet));
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        public async Task<HttpResponseMessage> PutModelAsync(PetDomainModel pet)
        {
            PetDomainModel domainPet = mapper.Map<PetDomainModel, PetDomainModel>(pet);
            await petService.PutPetAsync(domainPet);
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        public async Task<HttpResponseMessage> DeletePetAsync(Guid id)
        {
            await petService.DeletePetAsync(id);
            return Request.CreateResponse(HttpStatusCode.OK);
        }
    }
}